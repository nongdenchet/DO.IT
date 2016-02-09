package apidez.com.doit.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import apidez.com.doit.R;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class PopCheckBox extends RelativeLayout {
    private final float SCALE_MIN = 0.0f;
    private final float SCALE_MAX = 1.0f;
    private final int DELAY_DURATION = 75;
    private final int ANIM_DURATION = 75;

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
        if (mCheck) {
            stateCheck();
        } else {
            stateUnCheck();
        }
    }

    public void animateChecked(boolean isChecked) {
        if (mCheck != isChecked) {
            mCheck = isChecked;
            doAnimation();
        }
    }

    private void doAnimation() {
        if (mCheck) {
            checkAnimation();
        } else {
            unCheckAnimation();
        }
    }

    private void stateUnCheck() {
        mCheckBoxFill.setScaleX(SCALE_MIN);
        mCheckBoxFill.setScaleY(SCALE_MIN);
        mCheckBox.setScaleX(SCALE_MAX);
        mCheckBox.setScaleY(SCALE_MAX);
    }

    private void stateCheck() {
        mCheckBoxFill.setScaleX(SCALE_MAX);
        mCheckBoxFill.setScaleY(SCALE_MAX);
        mCheckBox.setScaleX(SCALE_MIN);
        mCheckBox.setScaleY(SCALE_MIN);
    }

    private void checkAnimation() {
        scaleAnimation(mCheckBox, 0, SCALE_MIN).start();
        scaleAnimation(mCheckBoxFill, DELAY_DURATION, SCALE_MAX).start();
    }

    private void unCheckAnimation() {
        scaleAnimation(mCheckBoxFill, 0, SCALE_MIN).start();
        scaleAnimation(mCheckBox, DELAY_DURATION, SCALE_MAX).start();
    }

    private ViewPropertyAnimator scaleAnimation(View view, int delay, float scale) {
        return view.animate().setInterpolator(new AccelerateInterpolator())
                .scaleX(scale)
                .scaleY(scale)
                .setStartDelay(delay)
                .setDuration(ANIM_DURATION);
    }
}
