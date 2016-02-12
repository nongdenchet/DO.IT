package apidez.com.doit.viewmodel;

import java.util.Date;

import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;
import rx.Observable;

/**
 * Created by nongdenchet on 2/12/16.
 */
public interface TodoDialogViewModel {

    /**
     * Bind due date
     */
    void setDate(Date dueDate);

    /**
     * Bind priority
     */
    void setPriority(Priority priority);

    /**
     * Bind title
     */
    void setTitle(String title);

    /**
     * Restore todo
     */
    void restore(Todo todo);

    /**
     * Observe toast
     */
    Observable<String> toast();

    /**
     * Save action
     */
    Observable<Long> save();
}
