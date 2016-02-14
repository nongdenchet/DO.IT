package apidez.com.doit.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 2/8/16.
 */
public abstract class BaseFragment extends Fragment {
    private BehaviorSubject<BaseFragment> mCreateView = BehaviorSubject.create();
    private BehaviorSubject<BaseFragment> mDestroyView = BehaviorSubject.create();

    public BehaviorSubject<BaseFragment> postCreateView() {
        return mCreateView;
    }
    public BehaviorSubject<BaseFragment> preDestroyView() {
        return mDestroyView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCreateView.onNext(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout(), container, false);
        ButterKnife.inject(this, rootView);
        onSetUpView(rootView);
        return rootView;
    }

    /**
     * Override to set up the views, called after view created
     */
    protected abstract void onSetUpView(View rootView);

    /**
     * Override to set the layout for fragment
     */
    protected abstract int layout();

    @Override
    public void onResume() {
        super.onResume();
        try {
            EventBus.getDefault().register(this);
        } catch (EventBusException ex) {
            Log.d("EventBus", ex.getMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        mDestroyView.onNext(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public <T> Observable<T> startObserve(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread())
                .takeUntil(preDestroyView());
    }

    public <T> Observable<T> startObserveInBackground(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .takeUntil(preDestroyView());
    }

    // Toast helpers
    public void showShortToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
