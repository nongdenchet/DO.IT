package apidez.com.doit.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import apidez.com.doit.R;
import apidez.com.doit.utils.DateUtils;

/**
 * Created by nongdenchet on 2/11/16.
 */
public class DueDateView extends RelativeLayout {
    private TextView mDayTitle, mSubtitle;
    private View mBackground;
    private Date mDate = null;

    public DueDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public DueDateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        inflate(getContext(), R.layout.due_date_view, this);
        initViews();
        initStyles(attrs);
    }

    private void initStyles(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.DueDateView);
        setDayTitle(attributes.getString(R.styleable.DueDateView_dayTitle));
        setSubtitle(attributes.getString(R.styleable.DueDateView_daySubtitle));
        select(attributes.getBoolean(R.styleable.DueDateView_select, false));
        attributes.recycle();
    }

    public void select(boolean isSelected) {
        mBackground.setBackgroundColor(ContextCompat.getColor(getContext(),
                isSelected ? R.color.due_date_enable : R.color.due_date_disable));
    }

    public Date getDate() {
        return mDate;
    }

    public void setInitDate(Date date) {
        this.mDate = date;
        setDayTitle(DateUtils.getDayFromDate(mDate));
    }

    private void initViews() {
        mDayTitle = (TextView) findViewById(R.id.dayTitle);
        mSubtitle = (TextView) findViewById(R.id.subtitle);
        mBackground = findViewById(R.id.content);
    }

    public void setPickDate(int year, int monthOfYear, int dayOfMonth) {
        mDate = DateUtils.create(year, monthOfYear, dayOfMonth);
        setDayTitle(DateUtils.getDayFromDate(mDate));
        setSubtitle(DateUtils.getMonthYearFromDate(mDate));
    }

    public void setDayTitle(String title) {
        mDayTitle.setText(title);
    }

    public void setSubtitle(String subtitle) {
        mSubtitle.setText(subtitle);
    }
}
