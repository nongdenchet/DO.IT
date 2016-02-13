package apidez.com.doit.viewmodel;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.model.Todo;
import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.utils.DataUtils;
import apidez.com.doit.utils.RxUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 2/13/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class TodoListViewModelImplTest {
    private TodoListViewModelImpl mViewModel;
    private List<TodoItemViewModel> mItemViewModelList;

    @Mock
    private TodoRepository mRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mRepository.getAll()).thenReturn(Observable.just(DataUtils.provideMockTodoList()));
        mViewModel = new TodoListViewModelImpl(RuntimeEnvironment.application, mRepository,
                new RxUtils.SchedulerHolder(Schedulers.immediate(), Schedulers.immediate()));
    }

    private void fetchData() {
        mViewModel.fetchAllTodo().toBlocking().single();
        mItemViewModelList = mViewModel.getTodoItems();
    }

    @Test
    public void testGetAlertVisibilityOnStart() throws Exception {
        assertEquals(View.GONE, mViewModel.getAlertVisibility().get());
    }

    @Test
    public void testGetAlertVisibilityVisibleNoItem() throws Exception {
        when(mRepository.getAll()).thenReturn(Observable.just(DataUtils.provideEmptyList()));
        mViewModel.fetchAllTodo().toBlocking().single();
        assertEquals(View.VISIBLE, mViewModel.getAlertVisibility().get());
    }

    @Test
    public void testGetAlertVisibilityGone() throws Exception {
        fetchData();
        assertEquals(View.GONE, mViewModel.getAlertVisibility().get());
    }

    @Test
    public void testFetchAllTodo() throws Exception {
        fetchData();
        assertThat(mItemViewModelList).hasSize(3);
        for (int i = 0; i < 3; i++) {
            assertEquals(Long.valueOf(i), mItemViewModelList.get(i).getTodo().getId());
        }
    }

    @Test
    public void testCheckChangeItemSuccess() throws Exception {
        when(mRepository.createOrUpdate(any(Todo.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return Observable.just((Todo) args[0]);
        });
        fetchData();
        mViewModel.checkChangeItem(mItemViewModelList.get(0)).toBlocking().single();
        assertTrue(mItemViewModelList.get(0).isCompleted());
    }

    @Test
    public void testCheckChangeItemFail() throws Exception {
        when(mRepository.createOrUpdate(any(Todo.class)))
                .thenReturn(Observable.create((Observable.OnSubscribe<Todo>) subscriber -> {
                    try {
                        throw new Exception("error");
                    } catch (Exception ex) {
                        subscriber.onError(ex);
                    }
                }));
        fetchData();
        mViewModel.checkChangeItem(mItemViewModelList.get(0)).subscribeOn(Schedulers.immediate())
                .subscribe(todo -> {
                    fail("Should not success");
                }, throwable -> {
                    assertEquals("error", throwable.getMessage());
                });
        assertFalse(mItemViewModelList.get(0).isCompleted());
    }

    @Test
    public void testInsert() throws Exception {
        mViewModel.insert(DataUtils.provideTodo());
        assertEquals(View.GONE, mViewModel.getAlertVisibility().get());
    }

    @Test
    public void testUpdate() throws Exception {
        fetchData();
        Todo todo = DataUtils.provideTodo();
        todo.setId(0L);
        mViewModel.update(todo);
        assertEquals("title", mItemViewModelList.get(0).getTitle());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        fetchData();
        Todo todo = DataUtils.provideTodo();
        todo.setId(100L);
        mViewModel.update(todo);
        for (int i = 0; i < 3; i++) {
            assertNotEquals(Long.valueOf(100), mItemViewModelList.get(i).getTodo().getId());
            assertNotEquals("title", mItemViewModelList.get(i).getTodo().getTitle());
        }
    }

    @Test
    public void testDeleteItemSuccess() throws Exception {
        when(mRepository.delete(anyLong())).thenReturn(Observable.just(true));
        fetchData();
        for (int i = 0; i < 3; i++) mViewModel.deleteItem(0).toBlocking().single();
        assertThat(mViewModel.getTodoItems()).hasSize(0);
        assertEquals(View.VISIBLE, mViewModel.getAlertVisibility().get());
    }

    @Test
    public void testDeleteItemFail() throws Exception {
        when(mRepository.delete(anyLong())).thenReturn(Observable.just(false));
        fetchData();
        mViewModel.deleteItem(0).toBlocking().single();
        assertThat(mViewModel.getTodoItems()).hasSize(3);
        assertEquals(View.GONE, mViewModel.getAlertVisibility().get());
    }
}