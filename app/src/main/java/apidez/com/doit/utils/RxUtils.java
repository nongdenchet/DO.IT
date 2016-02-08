package apidez.com.doit.utils;

import android.widget.RadioButton;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by nongdenchet on 1/17/16.
 */
public class RxUtils {

    public static class SchedulerHolder {
        public Scheduler mainScheduler;
        public Scheduler ioScheduler;

        public SchedulerHolder(Scheduler mainScheduler, Scheduler ioScheduler) {
            this.mainScheduler = mainScheduler;
            this.ioScheduler = ioScheduler;
        }
    }

    public static void bindRadioButton(final RadioButton radioButton, Observable<Boolean> stream) {
        stream.observeOn(AndroidSchedulers.mainThread())
                .subscribe(radioButton::setChecked);
    }
}
