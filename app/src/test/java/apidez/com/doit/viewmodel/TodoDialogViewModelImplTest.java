package apidez.com.doit.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;
import apidez.com.doit.repository.TodoRepository;
import apidez.com.doit.utils.DataUtils;
import apidez.com.doit.utils.RxUtils;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 2/13/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class TodoDialogViewModelImplTest {
    private TodoDialogViewModelImpl mViewModel;
    private TestSubscriber mTestSubscriber1 = TestSubscriber.create();
    private TestSubscriber mTestSubscriber2 = TestSubscriber.create();

    @Mock
    private TodoRepository mRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mRepository.createOrUpdate(any(Todo.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return Observable.just((Todo) args[0]);
        });
        mViewModel = new TodoDialogViewModelImpl(RuntimeEnvironment.application, mRepository,
                new RxUtils.SchedulerHolder(Schedulers.immediate(), Schedulers.immediate()));
    }

    @Test
    public void testSaveWithoutTitle() throws Exception {
        mViewModel.toast().subscribe(mTestSubscriber1);
        mViewModel.save().subscribe(mTestSubscriber2);
        mTestSubscriber1.assertValue("Title is required");
        mTestSubscriber2.assertNoValues();
    }

    @Test
    public void testSaveWithEmptyTitle() throws Exception {
        mViewModel.setTitle("     ");
        mViewModel.toast().subscribe(mTestSubscriber1);
        mViewModel.save().subscribe(mTestSubscriber2);
        mTestSubscriber1.assertValue("Title is required");
        mTestSubscriber2.assertNoValues();
    }

    @Test
    public void testSaveWithDefaultPriority() throws Exception {
        mViewModel.setTitle("title");
        Todo todo = mViewModel.save().toBlocking().single();
        assertEquals(Priority.HIGH, todo.getPriority());
    }

    @Test
    public void testSaveWithTitle() throws Exception {
        mViewModel.setTitle("title");
        Todo todo = mViewModel.save().toBlocking().single();
        assertEquals("title", todo.getTitle());
    }

    @Test
    public void testSaveWithPriority() throws Exception {
        mViewModel.setTitle("title");
        mViewModel.setPriority(Priority.LOW);
        Todo todo = mViewModel.save().toBlocking().single();
        assertEquals(Priority.LOW, todo.getPriority());
    }

    @Test
    public void testSaveWithDueDate() throws Exception {
        Date date = new Date();
        mViewModel.setTitle("title");
        mViewModel.setDate(date);
        Todo todo = mViewModel.save().toBlocking().single();
        assertEquals(date, todo.getDueDate());
    }

    @Test
    public void testSaveUpdate() throws Exception {
        Todo todo = DataUtils.provideTodo();
        todo.setId(100L);
        mViewModel.restore(todo);
        Todo newTodo = mViewModel.save().toBlocking().single();
        assertTrue(todo.equals(newTodo));
    }
}