package apidez.com.doit.dependency.module;

import android.content.Context;

import javax.inject.Singleton;

import apidez.com.doit.utils.RxUtils;
import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nongdenchet on 2/8/16.
 */
@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }

    @Provides
    public RxUtils.SchedulerHolder provideSchedulerHolder() {
        return new RxUtils.SchedulerHolder(AndroidSchedulers.mainThread(), Schedulers.io());
    }
}
