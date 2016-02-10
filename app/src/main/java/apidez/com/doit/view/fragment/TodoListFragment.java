package apidez.com.doit.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import apidez.com.doit.DoItApp;
import apidez.com.doit.R;
import apidez.com.doit.databinding.FragmentTodoListBinding;
import apidez.com.doit.dependency.module.TodoListModule;
import apidez.com.doit.view.adapter.TodoListAdapter;
import apidez.com.doit.view.custom.DisableLinearLayoutManager;
import apidez.com.doit.viewmodel.TodoListViewModel;
import butterknife.InjectView;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListFragment extends BaseFragment {
    private TodoListAdapter mTodoListAdapter;
    private FragmentTodoListBinding mBinding;

    @InjectView(R.id.todoList)
    RecyclerView mTodoList;

    @InjectView(R.id.swipeContainer)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    TodoListViewModel mViewModel;

    public static TodoListFragment newInstance() {
        Bundle args = new Bundle();
        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DoItApp.app().component()
                .plus(new TodoListModule())
                .inject(this);
    }

    @Override
    protected int layout() {
        return R.layout.fragment_todo_list;
    }

    @Override
    protected void onSetUpView(View rootView) {
        bindViewModel(rootView);
        setUpRecyclerView();
        setUpSwipe();
    }

    private void setUpRecyclerView() {
        mTodoList.setLayoutManager(new DisableLinearLayoutManager(getContext(), false));
        mTodoListAdapter = new TodoListAdapter(getContext());
        mTodoList.setAdapter(mTodoListAdapter);
        startObserve(mTodoListAdapter.animationEnd()).subscribe(done -> {
            if (done) {
                mTodoList.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }

    private void setUpSwipe() {
        mSwipeRefreshLayout.setOnRefreshListener(this::fetchData);
    }

    private void bindViewModel(View rootView) {
        mBinding = DataBindingUtil.bind(rootView);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_todo_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addAction() {
        // TODO: implement this
        mViewModel.getTodoItems().remove(0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);
        fetchData();
    }

    private void fetchData() {
        startObserve(mViewModel.fetchAllTodo()).subscribe(response -> {
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    // Events
    public void onEvent(TodoListAdapter.CheckItemEvent event) {
        startObserve(mViewModel.checkChangeItem(event.decorator)).subscribe(checked -> {
            event.callBack.onCheckChange(checked);
        }, throwable -> {
            showShortToast(throwable.getMessage());
        });
    }
}
