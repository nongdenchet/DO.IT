package apidez.com.doit.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.List;

import apidez.com.doit.decorator.TodoDecorator;
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
    private ObservableList<TodoDecorator> mTodoItems = new ObservableArrayList<>();

    public TodoListViewModelImpl(@NonNull Context mContext, @NonNull TodoRepository todoRepository,
                                 @NonNull RxUtils.SchedulerHolder schedulerHolder) {
        super(schedulerHolder);
        this.mContext = mContext;
        this.mRepository = todoRepository;
    }

    @Override
    public ObservableList<TodoDecorator> getTodoItems() {
        return mTodoItems;
    }

    @Override
    public Observable fetchAllTodo() {
        //noinspection Anonymous2MethodRef
        return configWithScheduler(Observable.create(new Observable.OnSubscribe<List<Todo>>() {
            @Override
            public void call(Subscriber<? super List<Todo>> subscriber) {
                List<Todo> items = mRepository.getAll();
                subscriber.onNext(items);
                subscriber.onCompleted();
            }
        })).doOnNext(items -> mTodoItems.addAll(TransformUtils.map(items, new TransformUtils.Map<Todo>() {
            @Override
            public TodoDecorator map(Todo todo) {
                return new TodoDecorator(todo);
            }
        })));
    }

    @Override
    public Observable<Boolean> checkChangeItem(TodoDecorator decorator) {
        return configWithScheduler(Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> {
            boolean isComplete = decorator.getChecked();
            try {
                // update check
                decorator.setChecked(!isComplete);
                mRepository.update(decorator.getTodo());
                subscriber.onNext(decorator.getChecked());
                subscriber.onCompleted();
            } catch (Exception ex) {
                // reverse check
                decorator.setChecked(isComplete);
                subscriber.onError(ex);
            }
        }));
    }
}
