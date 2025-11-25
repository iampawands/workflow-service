package workflowservice;

import workflowservice.model.TaskNode;
import workflowservice.model.WorkflowContext;
import workflowservice.model.WorkflowDefinition;
import workflowservice.model.SimpleTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkflowDemo {
    public static void main(String[] args) {
        WorkflowDefinition wf = new WorkflowDefinition("SampleWorkflow");

        TaskNode t1 = new TaskNode("T1", new SimpleTask("T1"), List.of());
        TaskNode t2 = new TaskNode("T2", new SimpleTask("T2"), List.of());
        TaskNode t3 = new TaskNode("T3", new SimpleTask("T3"), List.of("T1", "T2"));
        TaskNode t4 = new TaskNode("T4", new SimpleTask("T4"), List.of("T3"));

        wf.addTask(t1);
        wf.addTask(t2);
        wf.addTask(t3);
        wf.addTask(t4);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        WorkflowEngine engine = new WorkflowEngine(executor);

        engine.run(wf, new WorkflowContext());

        executor.shutdown();
    }
}
