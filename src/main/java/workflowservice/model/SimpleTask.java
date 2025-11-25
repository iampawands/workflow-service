package workflowservice.model;

/**
 * Simple implementation of {@link WorkflowTask} used for demonstration.
 * <p>
 * This task simulates some work by sleeping for one second and printing the
 * current thread name.
 */
public class SimpleTask implements WorkflowTask {


    private final String name;


    /**
     * Creates a new SimpleTask with the given name.
     *
     * @param name the task name
     */
    public SimpleTask(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public void execute(WorkflowContext context) throws Exception {
        System.out.printf("[Task %s] Starting on thread %s%n", name,
                Thread.currentThread().getName());


        // Simulate some work
        Thread.sleep(1000L);


        System.out.printf("[Task %s] Finished%n", name);
    }
}