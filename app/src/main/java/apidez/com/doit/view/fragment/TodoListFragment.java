package apidez.com.doit.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import apidez.com.doit.DoItApp;
import apidez.com.doit.R;
import apidez.com.doit.databinding.FragmentTodoListBinding;
import apidez.com.doit.dependency.module.TodoListModule;
import apidez.com.doit.view.adapter.TodoListAdapter;
import apidez.com.doit.view.viewholder.TodoItemViewHolder;
import apidez.com.doit.viewmodel.TodoListViewModel;
import butterknife.InjectView;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListFragment extends BaseFragment {
    private TodoListAdapter mTodoListAdapter;
    private FragmentTodoListBinding mBinding;

    @InjectView(R.id.addButton)
    FloatingActionButton mAddButton;

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

    private void bindViewModel(View rootView) {
        mBinding = DataBindingUtil.bind(rootView);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Add button
        mAddButton.setOnClickListener(v -> Log.d("Quan", getClass().getSimpleName()));

        // Fetch data
        startObserve(mViewModel.fetchAllTodo()).subscribe(response -> {
        });
    }

    private void setUpRecyclerView() {
        mTodoList.setLayoutManager(new LinearLayoutManager(getContext()));
        mTodoListAdapter = new TodoListAdapter(getContext());
        mTodoList.setAdapter(mTodoListAdapter);
    }

    private void setUpSwipe() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // TODO: implement this
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                mSwipeRefreshLayout.setRefreshing(false);
            }, 1000);
        });
    }

    // Events
    public void onEvent(TodoItemViewHolder.CheckItemEvent event) {
        startObserve(mViewModel.checkChangeItem(event.decorator))
                .subscribe(checked -> {
                    event.callBack.onCheckChange(checked);
                }, throwable -> {
                    showShortToast(throwable.getMessage());
                });
    }
}
