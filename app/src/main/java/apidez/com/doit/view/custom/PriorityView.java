package apidez.com.doit.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apidez.com.doit.R;
import apidez.com.domain.model.Priority;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class PriorityView extends RelativeLayout {
    private int mTitleColor;
    private boolean isChecked = false;
    private boolean isSelected = true;
    private Priority mPriority;

    private int[] mPriorityColor = new int[]{
            R.color.bg_priority_low,
            R.color.bg_priority_medium,
            R.color.bg_priority_high
    };

    private int[] mPriorityTitleColor = new int[]{
            android.R.color.black,
            android.R.color.black,
            android.R.color.white
    };

    private TextView mTitle;
    private CircleImageView mBackgroundDisable;
    private CircleImageView mBackground;

    public PriorityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public PriorityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        inflate(getContext(), R.layout.priority_view, this);
        initViews();
        initStyles(attrs);
    }

    private void initStyles(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.PriorityView);
        mTitle.setText(attributes.getString(R.styleable.PriorityView_priorityTitle));
        setPriorityColor(attributes.getResourceId(R.styleable.PriorityView_priorityColor, R.color.bg_priority_high));
        setPriorityTitleColor(attributes.getResourceId(R.styleable.PriorityView_priorityTitleColor, android.R.color.white));
        attributes.recycle();
    }

    public Priority getPriority() {
        return mPriority;
    }

    private void initViews() {
        mTitle = (TextView) findViewById(R.id.title);
        mBackground = (CircleImageView) findViewById(R.id.background);
        mBackgroundDisable = (CircleImageView) findViewById(R.id.background_disable);
    }

    public void select() {
        mTitle.setTextColor(ContextCompat.getColor(getContext(), mTitleColor));
        mBackground.setVisibility(VISIBLE);
        mBackgroundDisable.setVisibility(GONE);
        isSelected = true;
    }

    public void unSelect() {
        mTitle.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
        mBackground.setVisibility(GONE);
        mBackgroundDisable.setVisibility(VISIBLE);
        isSelected = false;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
        setTitle(priority.toString());
        setPriorityColor(mPriorityColor[priority.ordinal()]);
        setPriorityTitleColor(mPriorityTitleColor[priority.ordinal()]);
    }

    private void setTitle(String title) {
        mTitle.setText(title);
    }

    private void setPriorityTitleColor(int color) {
        mTitleColor = color;
        mTitle.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    private void setPriorityColor(int color) {
        mBackground.setImageResource(color);
    }
}
