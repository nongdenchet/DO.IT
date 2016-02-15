package apidez.com.doit.view.adapter;

import android.animation.Animator;

import apidez.com.doit.utils.view.EspressoIdlingResource;

/**
 * Created by nongdenchet on 2/10/16.
 */
public class AnimationAdapter implements Animator.AnimatorListener {

    @Override
    public void onAnimationStart(Animator animation) {
        EspressoIdlingResource.increment();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        EspressoIdlingResource.decrement();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }
}
