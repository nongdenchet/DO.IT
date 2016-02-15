package apidez.com.doit;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by nongdenchet on 10/23/15.
 */
public class TestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(@NonNull ClassLoader classLoader, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(classLoader, TestApplication.class.getName(), context);
    }
}