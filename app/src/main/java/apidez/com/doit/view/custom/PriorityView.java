package apidez.com.doit.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
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
    private TextView mTitle;
    private CircleImageView mBackground;
    private boolean isChecked = false;

    public PriorityView(Context context) {
        super(context);
        initialize();
    }

    public PriorityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public PriorityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PriorityView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.priority_view, this);
        initViews();
    }

    private void initViews() {
        mTitle = (TextView) findViewById(R.id.title);
        mBackground = (CircleImageView) findViewById(R.id.background);
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
