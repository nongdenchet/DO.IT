package apidez.com.doit.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import apidez.com.doit.R;
import apidez.com.doit.view.adapter.AnimationAdapter;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class PopCheckBox extends RelativeLayout {
    private final float SCALE_MIN = 0.0f;
    private final float SCALE_MAX = 1.0f;
    private final int DELAY_DURATION = 75;
    private final int ANIM_DURATION = 75;

    private boolean isChecked = false;
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

    private void initialize() {
        inflate(getContext(), R.layout.pop_checkbox, this);
        initViews();
    }

    private void initViews() {
        mCheckBox = (ImageView) findViewById(R.id.checkbox);
        mCheckBoxFill = (ImageView) findViewById(R.id.checkbox_fill);
    }

    public void setCheck(boolean isChecked) {
        this.isChecked = isChecked;
        if (this.isChecked) {
            stateCheck();
        } else {
            stateUnCheck();
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void animateChecked(boolean isChecked) {
        if (this.isChecked != isChecked) {
            this.isChecked = isChecked;
            doAnimation();
        }
    }

    private void doAnimation() {
        if (isChecked) {
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
                .setListener(new AnimationAdapter())
                .setDuration(ANIM_DURATION);
    }
}
