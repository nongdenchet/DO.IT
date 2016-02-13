package apidez.com.doit.viewmodel;

import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import apidez.com.doit.model.Todo;
import rx.Observable;

/**
 * Created by nongdenchet on 2/8/16.
 */
public interface TodoListViewModel {
    /**
     * Get all to-do decorator
     */
    ObservableList<TodoItemViewModel> getTodoItems();

    /**
     * Fetch data from database
     */
    Observable fetchAllTodo();

    /**
     * Check complete
     */
    Observable<Todo> checkChangeItem(TodoItemViewModel todoItemViewModel);

    /**
     * Delete object
     */
    Observable<Boolean> deleteItem(int position);

    /**
     * Alert visibility
     */
    ObservableInt getAlertVisibility();

    /**
     * Insert a new to-do
     */
    void insert(Todo todo);

    /**
     * Update a todo
     */
    void update(Todo todo);
}
