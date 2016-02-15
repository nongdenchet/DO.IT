package apidez.com.doit;

import android.content.Context;

import com.orm.SugarApp;

import apidez.com.doit.dependency.component.AppComponent;
import apidez.com.doit.dependency.component.DaggerAppComponent;
import apidez.com.doit.dependency.module.AppModule;
import apidez.com.doit.dependency.module.StorageModule;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class DoItApp extends SugarApp {
    private static Context mContext;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .storageModule(storageModule())
                .build();
    }

    protected StorageModule storageModule() {
        return new StorageModule();
    }

    public static DoItApp app() {
        return (DoItApp) mContext;
    }

    public AppComponent component() {
        return mAppComponent;
    }
}
