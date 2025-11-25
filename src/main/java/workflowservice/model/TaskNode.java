package workflowservice.model;


import java.util.List;

/**
 * Represents a node in the workflow graph, wrapping a {@link WorkflowTask}
 * and maintaining a list of dependencies on other tasks by name.
 */
public class TaskNode {

    private final String name;
    private final WorkflowTask task;
    private final List<String> dependencies;

    /**
     * Creates a new TaskNode with no dependencies.
     *
     * @param name the unique task name
     * @param task the underlying workflow task implementation
     */
    public TaskNode(String name, WorkflowTask task) {
        this(name, task, java.util.Collections.emptyList());
    }

    /**
     * Creates a new TaskNode with an explicit dependency list.
     *
     * @param name         the unique task name
     * @param task         the underlying workflow task implementation
     * @param dependencies the names of tasks this task depends on
     */
    public TaskNode(String name, WorkflowTask task, java.util.List<String> dependencies) {
        this.name = name;
        this.task = task;
        // copy into mutable list so we can add more dependencies later if needed
        this.dependencies = new java.util.ArrayList<>(dependencies);
    }

    /** Returns the task name. */
    public String getName() {
        return name;
    }

    /** Returns the associated {@link WorkflowTask}. */
    public WorkflowTask getTask() {
        return task;
    }

    /**
     * Adds a dependency on another task.
     *
     * @param dependencyName the name of the task this task depends on
     */
    public void addDependency(String dependencyName) {
        dependencies.add(dependencyName);
    }

    /**
     * Returns the list of dependency task names.
     *
     * @return the list of dependency names
     */
    public java.util.List<String> getDependencies() {
        return dependencies;
    }
}