package apidez.com.doit.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import apidez.com.doit.R;
import apidez.com.doit.model.Priority;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 2/11/16.
 */
public class PriorityPicker extends LinearLayout {
    private PriorityView mHighPriorityView, mMediumPriorityView, mLowPriorityView;

    /**
     * Contains all the priority views
     */
    private PriorityView[] mPriorityViews;

    /**
     * How data of this picker
     */
    private BehaviorSubject<Priority> mPriority = BehaviorSubject.create(Priority.HIGH);

    public PriorityPicker(Context context) {
        super(context);
        initialize();
    }

    public PriorityPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public PriorityPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.priority_picker, this);
        initViews();
        initPriorities();
        initActions();
    }

    private void initViews() {
        mHighPriorityView = (PriorityView) findViewById(R.id.priority_high);
        mMediumPriorityView = (PriorityView) findViewById(R.id.priority_medium);
        mLowPriorityView = (PriorityView) findViewById(R.id.priority_low);
        mPriorityViews = new PriorityView[]{mLowPriorityView, mMediumPriorityView, mHighPriorityView};
    }

    private void initPriorities() {
        mHighPriorityView.setPriority(Priority.HIGH);
        mMediumPriorityView.setPriority(Priority.MED);
        mLowPriorityView.setPriority(Priority.LOW);
    }

    private void initActions() {
        for (PriorityView priorityView : mPriorityViews) {
            priorityView.setOnClickListener(v -> selectPriority(priorityView));
        }
        selectDefaultPriority();
    }

    private void selectDefaultPriority() {
        selectPriority(mHighPriorityView);
    }

    private void selectPriority(PriorityView priorityView) {
        unSelectAllPriorityViews();
        priorityView.select();
        mPriority.onNext(priorityView.getPriority());
    }

    private void unSelectAllPriorityViews() {
        for (PriorityView priorityView : mPriorityViews) {
            priorityView.unSelect();
        }
    }

    public void setPriority(Priority priority) {
        selectPriority(mPriorityViews[priority.ordinal()]);
    }

    public Observable<Priority> priority() {
        return mPriority.asObservable();
    }
}
