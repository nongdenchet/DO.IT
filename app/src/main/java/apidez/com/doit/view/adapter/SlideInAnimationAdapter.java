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
    private final int DELAY = 400;
    private final int SLIDE_DURATION = 900;
    private final int FADE_DURATION = 500;

    private int mLastPosition = -1;
    private int mItemViewHeight = 0;
    protected Context mContext;

    public SlideInAnimationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void setItems(List<T> items) {
        if (mLastPosition == lastAnimatePosition()) { // Animation end
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
                    runFadeInAnimation(view, position, FADE_DURATION);
                    runSlideInAnimation(view, position, SLIDE_DURATION, slideAnimListener());
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

    private void runSlideInAnimation(View view, int position, int duration, Animator.AnimatorListener listener) {
        view.setTranslationY(UiUtils.dpToPx(mContext, mItemViewHeight));
        view.animate().setInterpolator(new DecelerateInterpolator(3.f))
                .setStartDelay(position * OFFSET + DELAY)
                .setListener(listener)
                .setDuration(duration)
                .translationY(0)
                .start();
    }

    private void runFadeInAnimation(View view, int position, int duration) {
        view.setAlpha(0.0f);
        view.animate().setInterpolator(new LinearInterpolator())
                .setStartDelay(position * OFFSET + DELAY)
                .setDuration(duration)
                .alpha(1.0f)
                .start();
    }

    private int lastAnimatePosition() {
        if (mItemViewHeight == 0) return getItemCount() - 1;
        return Math.min(getItemCount(), (int) Math.ceil(
                (float) UiUtils.getContentHeightWithoutToolbar(mContext) / mItemViewHeight)) - 1;
    }
}
