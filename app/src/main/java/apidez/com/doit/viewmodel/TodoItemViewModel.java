package apidez.com.doit.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.text.format.DateFormat;
import android.view.View;

import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoItemViewModel extends BaseObservable {
    private final String NO_DUE_DATE = "No due date";

    private ObservableBoolean mEnableState = new ObservableBoolean(true);
    private ObservableInt mActionVisibility = new ObservableInt(View.GONE);
    private ObservableInt mDividerVisibility = new ObservableInt(View.VISIBLE);
    private ObservableInt mDisableVisibility = new ObservableInt(View.INVISIBLE);

    private Todo mTodo;

    public TodoItemViewModel(Todo todo) {
        this.mTodo = todo;
    }

    public Todo getTodo() {
        return mTodo;
    }

    // Observable properties

    public ObservableInt getActionVisibility() {
        return mActionVisibility;
    }

    public ObservableInt getDisableVisibility() {
        return mDisableVisibility;
    }

    public ObservableBoolean getEnableState() {
        return mEnableState;
    }

    public ObservableInt getDividerVisibility() {
        return mDividerVisibility;
    }

    // Action

    public void resetState() {
        mEnableState.set(true);
        mActionVisibility.set(View.GONE);
        mDividerVisibility.set(View.VISIBLE);
        mDisableVisibility.set(View.INVISIBLE);
    }

    public void switchEnableWhenNotChoose() {
        mDisableVisibility.set(mDisableVisibility.get() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
        mEnableState.set(!mEnableState.get());
    }

    public void switchActionVisibility() {
        mActionVisibility.set(mActionVisibility.get() == View.GONE ? View.VISIBLE : View.GONE);
        mDividerVisibility.set(mDividerVisibility.get() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    public boolean isCompleted() {
        return mTodo.isCompleted();
    }

    // Properties

    public float getOpacity() {
        return mTodo.isCompleted() ? 0.5f : 1.0f;
    }

    public String getTitle() {
        return mTodo.getTitle();
    }

    public String getDueDate() {
        return mTodo.getDueDate() == null ? NO_DUE_DATE : DateFormat.format("dd/MM/yyyy", mTodo.getDueDate()).toString();
    }

    public Priority getPriority() {
        return mTodo.getPriority();
    }
}
