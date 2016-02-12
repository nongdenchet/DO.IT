package apidez.com.doit.dependency.fake;

import java.util.List;

import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.utils.DataUtils;
import apidez.com.doit.model.Todo;
import rx.Observable;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class FakeTodoRepositoryImpl implements TodoRepository {

    @Override
    public Observable<Long> createOrUpdate(Todo todo) {
        return Observable.just(0L);
    }

    @Override
    public Observable<List<Todo>> getAll() {
        return Observable.just(DataUtils.provideMockTodoList());
    }

    @Override
    public Observable<Boolean> delete(Long id) {
        return Observable.just(true);
    }
}
