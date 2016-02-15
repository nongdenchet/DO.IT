package apidez.com.doit;

import android.content.Context;

import apidez.com.doit.dependency.module.StorageModule;
import apidez.com.doit.repository.FakeTodoRepositoryImpl;
import apidez.com.doit.repository.TodoRepository;

/**
 * Created by nongdenchet on 10/23/15.
 */
public class TestApplication extends DoItApp {
    private TodoRepository mTodoRepository = new FakeTodoRepositoryImpl();

    @Override
    protected StorageModule storageModule() {
        return new StorageModule() {

            @Override
            public TodoRepository provideTodoRepository(Context context) {
                return mTodoRepository;
            }
        };
    }

    public void setTodoRepository(TodoRepository todoRepository) {
        this.mTodoRepository = todoRepository;
    }
}
