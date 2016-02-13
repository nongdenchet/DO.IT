package apidez.com.doit.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Date;

import apidez.com.doit.R;
import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;
import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.utils.RxUtils;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 2/12/16.
 */
public class TodoDialogViewModelImpl extends BaseViewModel implements TodoDialogViewModel {
    private Context mContext;
    private boolean isUpdate = false;
    private TodoRepository mRepository;
    private BehaviorSubject<String> mToast = BehaviorSubject.create();

    private Priority mPriority = Priority.HIGH;
    private Date mDueDate;
    private String mTitle;
    private Todo mTodo;

    public TodoDialogViewModelImpl(@NonNull Context context, @NonNull TodoRepository todoRepository,
                                   @NonNull RxUtils.SchedulerHolder schedulerHolder) {
        super(schedulerHolder);
        this.mContext = context;
        this.mRepository = todoRepository;
    }

    @Override
    public void setDate(Date dueDate) {
        this.mDueDate = dueDate;
    }

    @Override
    public void setPriority(Priority priority) {
        this.mPriority = priority;
    }

    @Override
    public void setTitle(String title) {
        this.mTitle = title.trim();
    }

    @Override
    public void restore(Todo todo) {
        isUpdate = true;
        mTodo = todo;
        mTitle = todo.getTitle();
        mDueDate = todo.getDueDate();
        mPriority = todo.getPriority();
    }

    @Override
    public Observable<String> toast() {
        return mToast.asObservable();
    }

    @Override
    public Observable<Todo> save() {
        if (emptyTitle()) {
            mToast.onNext(mContext.getString(R.string.title_require));
            return Observable.empty();
        }
        return mRepository.createOrUpdate(prepareTodo());
    }

    private boolean emptyTitle() {
        return TextUtils.isEmpty(mTitle);
    }

    private Todo prepareTodo() {
        return isUpdate ? updateTodo() : createTodo();
    }

    private Todo updateTodo() {
        mTodo.setDueDate(mDueDate);
        mTodo.setPriority(mPriority);
        mTodo.setTitle(mTitle);
        return mTodo;
    }

    private Todo createTodo() {
        return new Todo.Builder(mTitle, mPriority)
                .dueDate(mDueDate)
                .completed(false)
                .build();
    }
}
