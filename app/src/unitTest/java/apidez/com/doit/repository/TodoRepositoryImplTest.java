package apidez.com.doit.repository;

import android.content.Context;

import com.orm.SugarDb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nongdenchet on 2/12/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class TodoRepositoryImplTest {
    private TodoRepositoryImpl mRepository;
    private Context mContext;
    private SugarDb mDatabase;

    @Before
    public void setUp() throws Exception {
        mRepository = new TodoRepositoryImpl();
        mContext = RuntimeEnvironment.application;

//        Field field = SugarApp.class.getDeclaredField("sugarDb");
//        field.setAccessible(true);
//        mDatabase = new SugarDb(RuntimeEnvironment.application);
//        field.set(SugarContext.getSugarContext(), mDatabase);
    }

    private List<Todo> getAllTodo() throws Exception {
        return mRepository.getAll().toBlocking().single();
    }

    @Test
    public void testCreate() throws Exception {
        Todo todo = new Todo.Builder("title", Priority.HIGH).build();
        mRepository.createOrUpdate(todo).toBlocking().single();
        assertThat(getAllTodo()).hasSize(1);
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }
}