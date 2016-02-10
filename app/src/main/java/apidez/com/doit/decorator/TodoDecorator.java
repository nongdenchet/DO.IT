package apidez.com.doit.decorator;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.text.format.DateFormat;

import apidez.com.doit.R;
import apidez.com.doit.model.Todo;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoDecorator extends BaseObservable {
    private final String NO_DUE_DATE = "No due date";
    private ObservableBoolean mEnableState = new ObservableBoolean(true);
    private Todo mTodo;

    private int[] mPriorityColor = new int[]{
            R.color.bg_priority_low,
            R.color.bg_priority_medium,
            R.color.bg_priority_high
    };

    private int[] mPriorityTitleColor = new int[]{
            android.R.color.black,
            android.R.color.black,
            android.R.color.white
    };

    public TodoDecorator(Todo todo) {
        this.mTodo = todo;
    }

    public Todo getTodo() {
        return mTodo;
    }

    public float getOpacity() {
        return mTodo.isCompleted() ? 0.5f : 1.0f;
    }

    public void setCompleted(boolean complete) {
        mTodo.setCompleted(complete);
        mEnableState.set(!complete);
    }

    public boolean isCompleted() {
        return mTodo.isCompleted();
    }

    public ObservableBoolean getEnableState() {
        return mEnableState;
    }

    public String getTitle() {
        return mTodo.getTitle();
    }

    public String getDueDate() {
        return mTodo.getDueDate() == null ? NO_DUE_DATE : DateFormat.format("dd/MM/yyyy", mTodo.getDueDate()).toString();
    }

    public String getPriority() {
        return mTodo.getPriority().name();
    }

    public int getPriorityColor() {
        return mPriorityColor[mTodo.getPriority().ordinal()];
    }

    public int getPriorityTitleColor() {
        return mPriorityTitleColor[mTodo.getPriority().ordinal()];
    }
}
