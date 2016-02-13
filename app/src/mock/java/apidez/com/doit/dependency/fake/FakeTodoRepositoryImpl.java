package apidez.com.doit.dependency.fake;

import com.google.common.collect.Lists;

import java.util.List;

import apidez.com.doit.model.Todo;
import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.utils.DataUtils;
import rx.Observable;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class FakeTodoRepositoryImpl implements TodoRepository {
    private static long ID = 13;
    private List<Todo> mTodoList;

    public FakeTodoRepositoryImpl() {
        mTodoList = Lists.reverse(DataUtils.provideLongMockTodoList());
    }

    @Override
    public Observable<Todo> createOrUpdate(Todo newTodo) {
        int foundId = findTodoById(newTodo.getId());
        if (foundId != -1) {
            mTodoList.set(foundId, newTodo);
        } else {
            newTodo.setId(ID++);
            mTodoList.add(0, newTodo);
        }
        return Observable.just(newTodo);
    }

    @Override
    public Observable<List<Todo>> getAll() {
        return Observable.just(DataUtils.provideLongMockTodoList());
    }

    @Override
    public Observable<Boolean> delete(Long id) {
        int foundId = findTodoById(id);
        if (foundId != -1) {
            mTodoList.remove(mTodoList.get(foundId));
            return Observable.just(true);
        }
        return Observable.just(false);
    }

    private int findTodoById(Long id) {
        for (int i = 0; i < mTodoList.size(); i++) {
            if (mTodoList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
