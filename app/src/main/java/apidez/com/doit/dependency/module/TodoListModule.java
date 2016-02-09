package apidez.com.doit.dependency.module;

import android.content.Context;

import apidez.com.doit.dependency.scope.ViewScope;
import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.utils.RxUtils;
import apidez.com.doit.viewmodel.TodoListViewModel;
import apidez.com.doit.viewmodel.TodoListViewModelImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 2/8/16.
 */
@Module
public class TodoListModule {

    @Provides
    @ViewScope
    public TodoListViewModel provideTodoListViewModel(Context context, TodoRepository todoRepository, RxUtils.SchedulerHolder schedulerHolder) {
        return new TodoListViewModelImpl(context, todoRepository, schedulerHolder);
    }
}
