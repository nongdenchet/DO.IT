package apidez.com.doit.viewmodel;

import android.databinding.BaseObservable;

import apidez.com.doit.utils.RxUtils;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by nongdenchet on 2/8/16.
 */
public abstract class BaseViewModel extends BaseObservable {
    protected Scheduler mMainThread, mIOThread;

    public BaseViewModel(RxUtils.SchedulerHolder schedulerHolder) {
        this.mMainThread = schedulerHolder.mainScheduler;
        this.mIOThread = schedulerHolder.ioScheduler;
    }

    public BaseViewModel() {
    }

    public <T> Observable<T> configWithScheduler(Observable<T> observable) {
        return observable.subscribeOn(mIOThread)
                .observeOn(mMainThread);
    }
}