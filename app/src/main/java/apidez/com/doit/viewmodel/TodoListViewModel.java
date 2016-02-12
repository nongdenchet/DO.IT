package apidez.com.doit.viewmodel;

import android.databinding.ObservableInt;
import android.databinding.ObservableList;

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
    Observable<Long> checkChangeItem(TodoItemViewModel todoItemViewModel);

    /**
     * Delete object
     */
    Observable<Boolean> deleteItem(int position);

    /**
     * Alert visibility
     */
    ObservableInt getAlertVisibility();

    /**
     * Observe background color
     */
    ObservableInt backgroundColor();

    /**
     * Set background disable/enable
     */
    void setEnableBackground(boolean enable);

    /**
     * Switch background disable/enable
     */
    void switchEnable();
}
