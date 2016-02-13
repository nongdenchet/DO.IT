package apidez.com.doit.repository;

import android.content.Context;

import com.google.common.collect.Lists;

import java.util.List;

import apidez.com.doit.R;
import apidez.com.doit.model.Todo;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoRepositoryImpl implements TodoRepository {
    private Context mContext;

    public TodoRepositoryImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public Observable<Todo> createOrUpdate(Todo todo) {
        return Observable.create(new Observable.OnSubscribe<Todo>() {
            @Override
            public void call(Subscriber<? super Todo> subscriber) {
                try {
                    long id = todo.save();
                    if (id == -1L) throw new Exception(mContext.getString(R.string.problem_save));
                    subscriber.onNext(todo);
                    subscriber.onCompleted();
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }

    @Override
    public Observable<Boolean> delete(Long id) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    Todo todo = Todo.findById(Todo.class, id);
                    if (todo == null) throw new Exception(mContext.getString(R.string.problem_find));
                    if (!todo.delete()) throw new Exception(mContext.getString(R.string.problem_delete));
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }

    @Override
    public Observable<List<Todo>> getAll() {
        return Observable.just(Lists.reverse(Todo.listAll(Todo.class)));
    }
}
