package workflowservice;


import workflowservice.model.TaskNode;
import workflowservice.model.WorkflowContext;
import workflowservice.model.WorkflowDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class WorkflowEngine {

    private final ExecutorService executor;

    public WorkflowEngine(ExecutorService executor) {
        this.executor = executor;
    }

    public void run(WorkflowDefinition workflow, WorkflowContext context) {
        Map<String, TaskNode> tasks = workflow.getTasks();
        Map<String, CompletableFuture<Void>> futures = new HashMap<>();

        // For each task, create its future based on dependency futures
        for (TaskNode node : tasks.values()) {
            createFutureForNode(node, tasks, futures, context);
        }

        // Wait for all tasks to finish
        CompletableFuture<Void> all =
                CompletableFuture.allOf(futures.values().toArray(new CompletableFuture[0]));

        try {
            all.join(); // or all.get()
        } catch (CompletionException e) {
            // handle failure at workflow level
            throw new RuntimeException("Workflow failed: " + workflow.getName(), e);
        }
    }

    private CompletableFuture<Void> createFutureForNode(TaskNode node,
                                                        Map<String, TaskNode> allTasks,
                                                        Map<String, CompletableFuture<Void>> futures,
                                                        WorkflowContext context) {
        // If already created, return
        if (futures.containsKey(node.getName())) {
            return futures.get(node.getName());
        }

        List<String> deps = node.getDependencies();
        CompletableFuture<Void> future;

        if (deps == null || deps.isEmpty()) {
            // No dependencies → run immediately
            future = CompletableFuture.runAsync(() -> runTask(node, context), executor);
        } else {
            // Get dependency futures (recursively create if needed)
            List<CompletableFuture<Void>> depFutures = deps.stream()
                    .map(depName -> {
                        TaskNode depNode = allTasks.get(depName);
                        if (depNode == null) {
                            throw new IllegalStateException("Missing dependency task: " + depName);
                        }
                        return createFutureForNode(depNode, allTasks, futures, context);
                    })
                    .collect(Collectors.toList());

            CompletableFuture<Void> allDeps =
                    CompletableFuture.allOf(depFutures.toArray(new CompletableFuture[0]));

            future = allDeps.thenRunAsync(() -> runTask(node, context), executor);
        }

        futures.put(node.getName(), future);
        return future;
    }

    private void runTask(TaskNode node, WorkflowContext context) {
        try {
            System.out.println("Executing task: " + node.getName() + " on thread " +
                    Thread.currentThread().getName());
            node.getTask().execute(context);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }
}