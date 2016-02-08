package apidez.com.doit.dependency.component;

import apidez.com.doit.dependency.module.TodoListModule;
import apidez.com.doit.dependency.scope.ViewScope;
import apidez.com.doit.view.fragment.TodoListFragment;
import dagger.Subcomponent;

/**
 * Created by nongdenchet on 2/8/16.
 */
@ViewScope
@Subcomponent(modules = {TodoListModule.class})
public interface TodoListComponent {
    void inject(TodoListFragment todoListFragment);
}
