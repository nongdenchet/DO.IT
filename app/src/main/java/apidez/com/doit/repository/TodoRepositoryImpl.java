package apidez.com.doit.repository;

import java.util.List;

import apidez.com.doit.model.Todo;
import apidez.com.doit.utils.DataUtils;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoRepositoryImpl implements TodoRepository {

    @Override
    public Todo create(Todo todo) {
        return todo;
    }

    @Override
    public List<Todo> getAll() {
        return DataUtils.provideMockTodoList();
    }

    @Override
    public boolean delete(String id) {
        return true;
    }

    @Override
    public boolean update(Todo todo) {
        return true;
    }
}
