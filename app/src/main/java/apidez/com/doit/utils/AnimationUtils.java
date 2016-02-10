package apidez.com.doit.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by nongdenchet on 2/9/16.
 */
public class AnimationUtils {

    /**
     * Animate the alpha of view
     * @param view : view to animate
     * @param alphaValue : the final alpha value
     * @param duration : the duration of animation
     */
    public static void animateAlpha(View view, float alphaValue, int duration) {
        view.animate().alpha(alphaValue)
                .setInterpolator(new LinearInterpolator())
                .setDuration(duration)
                .start();
    }
}
