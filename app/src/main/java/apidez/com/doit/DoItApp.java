package apidez.com.doit;

import android.content.Context;

import com.orm.SugarApp;

import apidez.com.doit.dependency.component.AppComponent;
import apidez.com.doit.dependency.component.DaggerAppComponent;
import apidez.com.doit.dependency.module.AppModule;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class DoItApp extends SugarApp {
    private AppComponent mAppComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static DoItApp app() {
        return (DoItApp) mContext;
    }

    public AppComponent component() {
        return mAppComponent;
    }
}
