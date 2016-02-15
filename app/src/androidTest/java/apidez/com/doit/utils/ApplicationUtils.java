package apidez.com.doit.utils;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;

import apidez.com.doit.TestApplication;
import apidez.com.doit.repository.TodoRepository;

/**
 * Created by nongdenchet on 10/22/15.
 */
public class ApplicationUtils {

    public static TestApplication application() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        return (TestApplication) instrumentation.getTargetContext().getApplicationContext();
    }

    public static void setTodoRepository(TodoRepository todoRepository) {
        application().setTodoRepository(todoRepository);
    }
}
