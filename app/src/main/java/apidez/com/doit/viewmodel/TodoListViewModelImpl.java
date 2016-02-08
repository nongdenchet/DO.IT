package apidez.com.doit.viewmodel;

import android.content.Context;

import apidez.com.doit.utils.RxUtils;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListViewModelImpl extends BaseViewModel implements TodoListViewModel {
    private Context mContext;

    public TodoListViewModelImpl(Context mContext, RxUtils.SchedulerHolder schedulerHolder) {
        super(schedulerHolder);
        this.mContext = mContext;
    }
}
