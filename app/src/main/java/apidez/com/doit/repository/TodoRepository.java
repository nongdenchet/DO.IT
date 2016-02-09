package apidez.com.doit.repository;

import java.util.List;

import apidez.com.doit.model.Todo;

/**
 * Created by nongdenchet on 2/8/16.
 */
public interface TodoRepository {
    /**
     * Create a new to-do
     */
    Todo create(Todo todo);

    /**
     * Get all to-do items
     */
    List<Todo> getAll();

    /**
     * Delete a to-do base on id
     */
    boolean delete(String id);

    /**
     * Update a to-do item
     */
    boolean update(Todo todo) throws Exception;
}
