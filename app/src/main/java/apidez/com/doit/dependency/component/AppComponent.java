package apidez.com.doit.dependency.component;

import javax.inject.Singleton;

import apidez.com.doit.dependency.module.AppModule;
import apidez.com.doit.dependency.module.StorageModule;
import apidez.com.doit.dependency.module.TodoListModule;
import dagger.Component;

/**
 * Created by nongdenchet on 2/8/16.
 */
@Singleton
@Component(modules = {AppModule.class, StorageModule.class})
public interface AppComponent {
    TodoListComponent plus(TodoListModule todoListModule);
}
