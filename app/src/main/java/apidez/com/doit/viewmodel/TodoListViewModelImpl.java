package apidez.com.doit.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import apidez.com.doit.model.Todo;
import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.utils.RxUtils;
import apidez.com.doit.utils.TransformUtils;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListViewModelImpl extends BaseViewModel implements TodoListViewModel {
    private Context mContext;
    private TodoRepository mRepository;
    private ObservableList<TodoItemViewModel> mTodoItems = new ObservableArrayList<>();

    private ObservableInt mAlertVisibility = new ObservableInt(View.GONE);

    public TodoListViewModelImpl(@NonNull Context mContext, @NonNull TodoRepository todoRepository,
                                 @NonNull RxUtils.SchedulerHolder schedulerHolder) {
        super(schedulerHolder);
        this.mContext = mContext;
        this.mRepository = todoRepository;
    }

    @Override
    public ObservableList<TodoItemViewModel> getTodoItems() {
        return mTodoItems;
    }

    @Override
    @SuppressWarnings("Anonymous2MethodRef")
    public Observable fetchAllTodo() {
        return configWithScheduler(Observable.create(new Observable.OnSubscribe<List<Todo>>() {
            @Override
            public void call(Subscriber<? super List<Todo>> subscriber) {
                List<Todo> items = mRepository.getAll();
                subscriber.onNext(items);
                subscriber.onCompleted();
            }
        })).doOnNext(items -> {
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
    public Observable<Boolean> checkChangeItem(Todo todo) {
        return configWithScheduler(Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> {
            try {
                todo.switchComplete();
                mRepository.update(todo);
                subscriber.onNext(todo.isCompleted());
                subscriber.onCompleted();
            } catch (Exception ex) {
                todo.switchComplete();
                subscriber.onError(ex);
            }
        }));
    }

    private void checkEmptyAndShowAlert() {
        mAlertVisibility.set(mTodoItems.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public ObservableInt getAlertVisibility() {
        return mAlertVisibility;
    }
}
