package apidez.com.doit.dependency.module;

import android.content.Context;
import android.support.annotation.NonNull;

import apidez.com.doit.dependency.scope.ViewScope;
import apidez.com.doit.utils.RxUtils;
import apidez.com.doit.viewmodel.TodoDialogViewModel;
import apidez.com.doit.viewmodel.TodoDialogViewModelImpl;
import apidez.com.doit.viewmodel.TodoListViewModel;
import apidez.com.doit.viewmodel.TodoListViewModelImpl;
import apidez.com.doit.repository.TodoRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 2/8/16.
 */
@Module
public class TodoListModule {

    @Provides
    @ViewScope
    public TodoListViewModel provideTodoListViewModel(@NonNull Context context, @NonNull TodoRepository todoRepository,
                                                      @NonNull RxUtils.SchedulerHolder schedulerHolder) {
        return new TodoListViewModelImpl(context, todoRepository, schedulerHolder);
    }

    @Provides
    @ViewScope
    public TodoDialogViewModel provideTodoDialogViewModel(@NonNull Context context, @NonNull TodoRepository todoRepository,
                                                        @NonNull RxUtils.SchedulerHolder schedulerHolder) {
        return new TodoDialogViewModelImpl(context, todoRepository, schedulerHolder);
    }
}
