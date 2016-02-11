package apidez.com.doit.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import apidez.com.doit.R;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 2/12/16.
 */

// TODO: remove duplication between BaseDialogFragment and BaseFragment
public abstract class BaseDialogFragment extends DialogFragment {
    private BehaviorSubject<BaseDialogFragment> mCreateView = BehaviorSubject.create();
    private BehaviorSubject<BaseDialogFragment> mDestroyView = BehaviorSubject.create();
    private ProgressDialog mProgressDialog;

    public BehaviorSubject<BaseDialogFragment> postCreateView() {
        return mCreateView;
    }
    public BehaviorSubject<BaseDialogFragment> preDestroyView() {
        return mDestroyView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProgressDialog();
        mCreateView.onNext(this);
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.loading_message));
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
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
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

    public <T> Observable<T> startObserveWithProgress(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> mProgressDialog.show())
                .doOnError(throwable -> mProgressDialog.hide())
                .doOnCompleted(() -> mProgressDialog.hide())
                .takeUntil(preDestroyView());
    }

    public <T> Observable<T> startObserveWithProgressInBackground(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mProgressDialog.show())
                .doOnError(throwable -> mProgressDialog.hide())
                .doOnCompleted(() -> mProgressDialog.hide())
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
