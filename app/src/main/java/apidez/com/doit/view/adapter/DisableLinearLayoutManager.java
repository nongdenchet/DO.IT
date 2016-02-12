package apidez.com.doit.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by nongdenchet on 2/10/16.
 */
public class DisableLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public DisableLinearLayoutManager(Context context, boolean scrollEnable) {
        super(context);
        this.isScrollEnabled = scrollEnable;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.isScrollEnabled = scrollEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
