package apidez.com.doit.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.Calendar;
import java.util.Date;

import apidez.com.doit.R;
import apidez.com.doit.utils.DateUtils;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 2/11/16.
 */
public class DueDatePicker extends RelativeLayout {
    private DueDateView mTodayView, mTomorrowView, mDayPicker, mNoDateView;
    private DueDateView[] mDueDateViews;
    private ListenerPickDate mListenerPickDate;

    private BehaviorSubject<Date> mDueDate = BehaviorSubject.create(new Date());

    public interface CallbackPickDate {
        void onDatePicked(int year, int monthOfYear, int dayOfMonth);
    }

    public interface ListenerPickDate {
        void pickDate(CallbackPickDate callbackPickDate);
    }

    public void setListenerPickDate(ListenerPickDate listenerPickDate) {
        this.mListenerPickDate = listenerPickDate;
    }

    public DueDatePicker(Context context) {
        super(context);
        initialize();
    }

    public DueDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public DueDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.due_date_picker, this);
        initViews();
        initActions();
        initDateValue();
    }

    private void initViews() {
        mTodayView = (DueDateView) findViewById(R.id.today);
        mTomorrowView = (DueDateView) findViewById(R.id.tomorrow);
        mDayPicker = (DueDateView) findViewById(R.id.date_picker);
        mNoDateView = (DueDateView) findViewById(R.id.no_due_date);
        mDueDateViews = new DueDateView[]{mTodayView, mTomorrowView, mDayPicker, mNoDateView};
    }

    private void initDateValue() {
        initToday();
        initTomorrow();
    }

    public void setDueDate(Date dueDate) {
        if (dueDate == null) {
            clearAndSelectView(mNoDateView);
        } else if (DateUtils.isToday(dueDate)) {
            clearAndSelectView(mTodayView);
        } else if (DateUtils.isTomorrow(dueDate)) {
            clearAndSelectView(mTomorrowView);
        } else {
            setDateForPicker(dueDate);
        }
    }

    private void setDateForPicker(Date dueDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dueDate);
        mDayPicker.setPickDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        clearAndSelectView(mDayPicker);
    }

    private void initTomorrow() {
        mTomorrowView.setInitDate(DateUtils.getTomorrow());
    }

    private void initToday() {
        mTodayView.setInitDate(new Date());
    }

    private void initActions() {
        for (DueDateView dueDateView : mDueDateViews) {
            dueDateView.setOnClickListener(v -> handleDueDateSelect(dueDateView));
        }
    }

    private void handleDueDateSelect(DueDateView dueDateView) {
        if (dueDateView == mDayPicker) {
            handlePickDate();
        } else {
            clearAndSelectView(dueDateView);
        }
    }

    private void clearAndSelectView(DueDateView dueDateView) {
        unSelectAllView();
        dueDateView.select(true);
        mDueDate.onNext(dueDateView.getDate());
    }

    private void handlePickDate() {
        if (mListenerPickDate != null) {
            mListenerPickDate.pickDate((year, monthOfYear, dayOfMonth) -> {
                mDayPicker.setPickDate(year, monthOfYear, dayOfMonth);
                clearAndSelectView(mDayPicker);
            });
        }
    }

    private void unSelectAllView() {
        for (DueDateView dueDateView : mDueDateViews) {
            dueDateView.select(false);
        }
    }

    public Observable<Date> date() {
        return mDueDate.asObservable();
    }
}
