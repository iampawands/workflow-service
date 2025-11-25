package workflowservice.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the definition of a workflow as a collection of named task nodes.
 */
public class WorkflowDefinition {


    private final String name;
    private final Map<String, TaskNode> tasks = new HashMap<>();


    /**
     * Creates a new workflow definition with the given name.
     *
     * @param name the workflow name
     */
    public WorkflowDefinition(String name) {
        this.name = name;
    }


    /**
     * Returns the workflow name.
     *
     * @return the workflow name
     */
    public String getName() {
        return name;
    }


    /**
     * Adds a task node to this workflow definition.
     *
     * @param node the task node to add
     */
    public void addTask(TaskNode node) {
        tasks.put(node.getName(), node);
    }


    /**
     * Returns the map of all task nodes by name.
     *
     * @return a map from task name to task node
     */
    public java.util.Map<String, TaskNode> getTasks() {
        return tasks;
    }
}
