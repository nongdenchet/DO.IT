package apidez.com.doit.view.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import apidez.com.doit.R;
import apidez.com.doit.view.adapter.TodoListAdapter;
import butterknife.InjectView;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListFragment extends BaseFragment {
    private TodoListAdapter mTodoListAdapter;

    @InjectView(R.id.addButton)
    FloatingActionButton mAddButton;

    @InjectView(R.id.todoList)
    RecyclerView mTodoList;

    @Override
    protected int layout() {
        return R.layout.fragment_todo_list;
    }

    @Override
    protected void onSetUpView(View rootView) {
        mAddButton.setOnClickListener(v -> Log.d("Quan", getClass().getSimpleName()));
        mTodoList.setLayoutManager(new LinearLayoutManager(getContext()));
        mTodoListAdapter = new TodoListAdapter(getContext());
        mTodoList.setAdapter(mTodoListAdapter);
    }
}
