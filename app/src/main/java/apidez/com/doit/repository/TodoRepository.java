package apidez.com.doit.repository;

import java.util.List;

import apidez.com.doit.model.Todo;
import rx.Observable;

/**
 * Created by nongdenchet on 2/8/16.
 */
public interface TodoRepository {
    /**
     * Create a new to-do or update old one
     * @return the id of the to-do
     */
    Observable<Todo> createOrUpdate(Todo todo);

    /**
     * Get all to-do items
     * @return list of to-do
     */
    Observable<List<Todo>> getAll();

    /**
     * Delete a to-do base on id
     * @return return boolean success or not
     */
    Observable<Boolean> delete(Long id);
}
