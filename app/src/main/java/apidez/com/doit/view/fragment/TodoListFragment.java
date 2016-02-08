package apidez.com.doit.view.fragment;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import apidez.com.doit.R;
import butterknife.InjectView;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListFragment extends BaseFragment {

    @InjectView(R.id.addButton)
    FloatingActionButton mAddButton;

    @Override
    protected int layout() {
        return R.layout.fragment_todo_list;
    }

    @Override
    protected void onSetUpView(View rootView) {
        mAddButton.setOnClickListener(v -> Log.d("Quan", getClass().getSimpleName()));
    }
}
