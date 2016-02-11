package apidez.com.doit.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apidez.com.doit.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class PriorityView extends RelativeLayout {
    private int mTitleColor = R.color.bg_priority_high;
    private boolean isChecked = false;
    private boolean isSelected = true;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PriorityView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        inflate(getContext(), R.layout.priority_view, this);
        initViews();
        initStyles(attrs);
    }

    private void initStyles(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.PriorityView);
        setTitle(attributes.getString(R.styleable.PriorityView_priorityTitle));
        setPriorityColor(attributes.getResourceId(R.styleable.PriorityView_priorityColor, R.color.bg_priority_high));
        setPriorityTitleColor(attributes.getResourceId(R.styleable.PriorityView_priorityTitleColor, android.R.color.white));
        attributes.recycle();
    }

    private void initViews() {
        mTitle = (TextView) findViewById(R.id.title);
        mBackground = (CircleImageView) findViewById(R.id.background);
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

    public void setPriorityTitleColor(int color) {
        mTitle.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setPriorityColor(int color) {
        mBackground.setImageResource(color);
    }
}
