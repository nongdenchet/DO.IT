package apidez.com.doit.dependency.module;

import android.content.Context;

import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.repository.TodoRepositoryImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 2/8/16.
 */
@Module
public class StorageModule {

    @Provides
    public TodoRepository provideTodoRepository(Context context) {
        return new TodoRepositoryImpl(context);
    }
}
