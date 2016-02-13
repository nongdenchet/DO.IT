package apidez.com.doit.repository;

import com.orm.SugarContext;
import com.orm.SugarRecord;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.model.Todo;
import apidez.com.doit.utils.DataUtils;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 2/13/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({Todo.class, SugarContext.class, SugarRecord.class})
public class TodoRepositoryImplTest {
    private TodoRepositoryImpl mTodoRepository;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setUp() throws Exception {
        mTodoRepository = new TodoRepositoryImpl(RuntimeEnvironment.application);
        PowerMockito.mockStatic(Todo.class);
        PowerMockito.mockStatic(SugarContext.class);
        PowerMockito.mockStatic(SugarRecord.class);
        PowerMockito.when(Todo.listAll(Todo.class)).thenReturn(DataUtils.provideLongMockTodoList());
    }

    @Test
    public void testCreateOrUpdateSuccess() throws Exception {
        Todo todo = Mockito.mock(Todo.class);
        when(todo.save()).thenReturn(1L);
        assertSame(todo, mTodoRepository.createOrUpdate(todo).toBlocking().single());
    }

    @Test
    public void testCreateOrUpdateFail() throws Exception {
        Todo todo = Mockito.mock(Todo.class);
        when(todo.save()).thenReturn(-1L);
        mTodoRepository.createOrUpdate(todo).observeOn(Schedulers.immediate()).subscribe(newTodo -> {
            fail("Should throw exception");
        }, throwable -> {
            assertEquals("Can not save todo", throwable.getMessage());
        });
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        PowerMockito.when(Todo.findById(Todo.class, 1L)).thenReturn(null);
        mTodoRepository.delete(1L).observeOn(Schedulers.immediate()).subscribe(newTodo -> {
            fail("Should throw exception");
        }, throwable -> {
            assertEquals("Can not find todo", throwable.getMessage());
        });
    }

    @Test
    public void testDeleteFail() throws Exception {
        Todo todo = Mockito.mock(Todo.class);
        when(todo.delete()).thenReturn(false);
        PowerMockito.when(Todo.findById(Todo.class, 1L)).thenReturn(todo);
        mTodoRepository.delete(1L).observeOn(Schedulers.immediate()).subscribe(newTodo -> {
            fail("Should throw exception");
        }, throwable -> {
            assertEquals("Can not delete todo", throwable.getMessage());
        });
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        Todo todo = Mockito.mock(Todo.class);
        when(todo.delete()).thenReturn(true);
        when(Todo.findById(Todo.class, 1L)).thenReturn(todo);
        assertTrue(mTodoRepository.delete(1L).toBlocking().single());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Todo> todoList = mTodoRepository.getAll().toBlocking().single();
        assertThat(todoList).hasSize(12);
        for (int i = 0; i < 12; i++) {
            assertTrue(todoList.get(i).getId().equals((long) (12 - i)));
        }
    }
}