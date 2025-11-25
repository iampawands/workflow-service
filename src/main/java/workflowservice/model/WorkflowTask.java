package workflowservice.model;

/**
 * Represents a unit of executable work in the workflow.
 * <p>
 * Implementations encapsulate a piece of business logic that can be run as
 * part of a workflow step.
 */
public interface WorkflowTask {


    /**
     * Returns the unique name of the task.
     *
     * @return the task name
     */
    String getName();


    /**
     * Executes the task's business logic.
     *
     * @param context shared workflow context for passing data between tasks
     * @throws Exception if any error occurs during execution
     */
    void execute(WorkflowContext context) throws Exception;
}
