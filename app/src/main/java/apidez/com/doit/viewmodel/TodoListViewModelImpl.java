package apidez.com.doit.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;

import apidez.com.doit.R;
import apidez.com.doit.model.Todo;
import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.utils.RxUtils;
import apidez.com.doit.utils.TransformUtils;
import rx.Observable;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListViewModelImpl extends BaseViewModel implements TodoListViewModel {
    private Context mContext;
    private TodoRepository mRepository;
    private boolean mEnableState = true;
    private ObservableInt mBackgroundColor = new ObservableInt(android.R.color.white);
    private ObservableList<TodoItemViewModel> mTodoItems = new ObservableArrayList<>();

    public TodoListViewModelImpl(@NonNull Context mContext, @NonNull TodoRepository todoRepository,
                                 @NonNull RxUtils.SchedulerHolder schedulerHolder) {
        super(schedulerHolder);
        this.mContext = mContext;
        this.mRepository = todoRepository;
    }

    private ObservableInt mAlertVisibility = new ObservableInt(View.GONE);

    @Override
    public ObservableInt getAlertVisibility() {
        return mAlertVisibility;
    }

    @Override
    public ObservableInt backgroundColor() {
        return mBackgroundColor;
    }

    @Override
    public ObservableList<TodoItemViewModel> getTodoItems() {
        return mTodoItems;
    }

    @Override
    @SuppressWarnings("Anonymous2MethodRef")
    public Observable fetchAllTodo() {
        return configWithScheduler(mRepository.getAll()).doOnNext(items -> {
            mTodoItems.clear();
            mTodoItems.addAll(TransformUtils.map(items, new TransformUtils.Map<Todo>() {
                @Override
                public TodoItemViewModel map(Todo todo) {
                    return new TodoItemViewModel(todo);
                }
            }));
            checkEmptyAndShowAlert();
        });
    }

    @Override
    public Observable<Long> checkChangeItem(TodoItemViewModel todoItemViewModel) {
        Todo todo = todoItemViewModel.getTodo();
        return configWithScheduler(mRepository.createOrUpdate(todo))
                .doOnSubscribe(todo::switchComplete)
                .doOnError(throwable -> todo.switchComplete())
                .doOnNext(id -> mTodoItems.set(mTodoItems.indexOf(todoItemViewModel), todoItemViewModel));
    }

    @Override
    public Observable<Boolean> deleteItem(int position) {
        TodoItemViewModel todoItemViewModel = mTodoItems.get(position);
        return mRepository.delete(todoItemViewModel.getTodo().getId()).doOnNext(success -> {
            if (success) {
                setEnableBackground(true);
                mTodoItems.remove(todoItemViewModel);
                checkEmptyAndShowAlert();
            }
        });
    }

    @Override
    public void setEnableBackground(boolean enable) {
        mEnableState = enable;
        mBackgroundColor.set(enable ? android.R.color.white : R.color.disable_color);
    }

    @Override
    public void switchEnable() {
        setEnableBackground(!mEnableState);
    }

    private void checkEmptyAndShowAlert() {
        mAlertVisibility.set(mTodoItems.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
