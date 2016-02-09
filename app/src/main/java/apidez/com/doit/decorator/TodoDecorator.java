package apidez.com.doit.decorator;

import android.databinding.BaseObservable;
import android.databinding.ObservableFloat;
import android.text.format.DateFormat;

import apidez.com.doit.R;
import apidez.com.doit.model.Todo;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoDecorator extends BaseObservable {
    private final String NO_DUE_DATE = "No due date";

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

    private Todo mTodo;
    private ObservableFloat mOpacity = new ObservableFloat(1.0f);

    public TodoDecorator(Todo todo) {
        this.mTodo = todo;
    }

    public Todo getTodo() {
        return mTodo;
    }

    public ObservableFloat getOpacity() {
        return mOpacity;
    }

    public void updateCheck(boolean complete) {
        mTodo.setCompleted(complete);
        mOpacity.set(complete ? 0.5f : 1.0f);
    }

    public boolean isCompleted() {
        return mTodo.isCompleted();
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
