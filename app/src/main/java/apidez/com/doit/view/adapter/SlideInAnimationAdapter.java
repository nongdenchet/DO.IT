package apidez.com.doit.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.List;

import apidez.com.doit.utils.UiUtils;
import apidez.com.doit.view.custom.AnimationAdapter;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 2/10/16.
 */
public abstract class SlideInAnimationAdapter<T> extends BaseRecyclerViewAdapter<T> {
    private final int OFFSET = 100;
    private final int DELAY = 300;
    private final int SLIDE_DURATION = 300;
    private final int FADE_DURATION = 300;

    private int mLastPosition = -1;
    private int mItemViewHeight = 0;
    protected Context mContext;

    public SlideInAnimationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void setItems(List<T> items) {
        if (mLastPosition == lastAnimatePosition() || mAnimationEndEvent.getValue()) { // Animation end
            super.setItems(items);
        } else {
            this.mItems = items;
            notifyDataSetChanged();
        }
    }

    protected BehaviorSubject<Boolean> mAnimationEndEvent = BehaviorSubject.create(false);

    public Observable<Boolean> animationEnd() {
        return mAnimationEndEvent.asObservable();
    }

    protected void animateItem(View view, int position) {
        if (position > lastAnimatePosition() || mAnimationEndEvent.getValue()) {
            return;
        }
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mItemViewHeight = view.getHeight();
                if (position > mLastPosition && position <= lastAnimatePosition()) {
                    mLastPosition = position;
                    runFadeInAnimation(view, position);
                    runSlideInAnimation(view, position, slideAnimListener());
                }
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    protected Animator.AnimatorListener slideAnimListener() {
        if (mLastPosition < lastAnimatePosition()) {
            return null;
        }
        return new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationEndEvent.onNext(true);
            }
        };
    }

    private void runSlideInAnimation(View view, int position, Animator.AnimatorListener listener) {
        view.setTranslationY(view.getHeight());
        view.animate().setInterpolator(new DecelerateInterpolator(3.f))
                .setStartDelay(position * OFFSET + DELAY)
                .setListener(listener)
                .setDuration(SLIDE_DURATION)
                .translationY(0)
                .start();
    }

    private void runFadeInAnimation(View view, int position) {
        view.setAlpha(0.0f);
        view.animate().setInterpolator(new LinearInterpolator())
                .setStartDelay(position * OFFSET + DELAY)
                .setDuration(FADE_DURATION)
                .alpha(1.0f)
                .start();
    }

    /**
     * Calculate the last item position (from 0) that the recycler view can display at the beginning
     */
    private int lastAnimatePosition() {
        if (mItemViewHeight == 0) return getItemCount() - 1;
        return Math.min(getItemCount(), (int) Math.ceil(
                (float) UiUtils.getContentHeightWithoutToolbar(mContext) / mItemViewHeight)) - 1;
    }
}
