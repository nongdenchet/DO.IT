package apidez.com.doit.decorator;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.text.format.DateFormat;
import android.view.View;

import apidez.com.doit.R;
import apidez.com.doit.model.Todo;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoDecorator extends BaseObservable {
    private final String NO_DUE_DATE = "No due date";
    private ObservableBoolean mEnableState = new ObservableBoolean(true);
    private ObservableInt mActionVisibility = new ObservableInt(View.GONE);
    private ObservableInt mDividerVisibility = new ObservableInt(View.VISIBLE);
    private ObservableInt mDisableVisibility = new ObservableInt(View.INVISIBLE);
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
