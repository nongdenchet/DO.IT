package apidez.com.doit.dependency.module;

import apidez.com.domain.repository.TodoRepository;
import apidez.com.doit.dependency.fake.FakeTodoRepositoryImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 2/8/16.
 */
@Module
public class StorageModule {

    @Provides
    public TodoRepository provideTodoRepository() {
        return new FakeTodoRepositoryImpl();
    }
}

