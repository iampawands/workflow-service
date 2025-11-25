package workflowservice.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Shared context object passed to each task during workflow execution.
 * <p>
 * Tasks can use this context to share data with other tasks in a
 * thread-safe manner.
 */
public class WorkflowContext {


    private final ConcurrentMap<String, Object> data = new ConcurrentHashMap<>();


    /**
     * Puts a key/value pair into the shared context.
     *
     * @param key   the key under which the value is stored
     * @param value the value to store
     */
    public void put(String key, Object value) {
        data.put(key, value);
    }


    /**
     * Retrieves a value from the shared context.
     *
     * @param key the key whose associated value is to be returned
     * @return the value, or {@code null} if none is associated with the key
     */
    public Object get(String key) {
        return data.get(key);
    }


    /**
     * Retrieves a value of the given type from the shared context.
     *
     * @param key   the key whose associated value is to be returned
     * @param clazz the expected type of the value
     * @param <T>   the type parameter
     * @return the value cast to the given type, or {@code null} if none is present
     * or cannot be cast
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }
        if (!clazz.isInstance(value)) {
            throw new ClassCastException("Value for key '" + key + "' is not of type " + clazz);
        }
        return clazz.cast(value);
    }
}
