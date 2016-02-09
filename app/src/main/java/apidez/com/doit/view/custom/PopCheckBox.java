package apidez.com.doit.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import apidez.com.doit.R;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class PopCheckBox extends RelativeLayout {
    private boolean mCheck = false;
    private ImageView mCheckBox;
    private ImageView mCheckBoxFill;

    public PopCheckBox(Context context) {
        super(context);
        initialize();
    }

    public PopCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public PopCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PopCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.pop_checkbox, this);
        initViews();
    }

    private void initViews() {
        mCheckBox = (ImageView) findViewById(R.id.checkbox);
        mCheckBoxFill = (ImageView) findViewById(R.id.checkbox_fill);
    }

    public void setCheck(boolean isChecked) {
        mCheck = isChecked;
        mCheckBoxFill.setVisibility(isChecked ? VISIBLE : INVISIBLE);
    }

    public void animateChecked(boolean isChecked) {
        mCheck = isChecked;

        // TODO: do the animation
        mCheckBoxFill.setVisibility(isChecked ? VISIBLE : INVISIBLE);
    }
}
