package apidez.com.doit.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import apidez.com.doit.R;
import apidez.com.doit.controller.TodoListController;
import apidez.com.doit.databinding.TodoItemBinding;
import apidez.com.doit.view.adapter.viewholder.TodoFooterViewHolder;
import apidez.com.doit.view.adapter.viewholder.TodoItemViewHolder;
import apidez.com.doit.viewmodel.TodoItemViewModel;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListAdapter extends SlideInAnimationAdapter<TodoItemViewModel> {
    private final int ITEM = 0;
    private final int FOOTER = 1;
    private TodoListController mTodoListController;

    public TodoListAdapter(Context context) {
        super(context);
        mTodoListController = new TodoListController(context, mItems, EventBus.getDefault());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mItems.size()) {
            return FOOTER;
        }
        return ITEM;
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    @Override
    public void setItems(List<TodoItemViewModel> items) {
        mTodoListController.setItems(items);
        super.setItems(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case FOOTER:
                View footer = LayoutInflater.from(mContext).inflate(R.layout.todo_item_footer, parent, false);
                return new TodoFooterViewHolder(footer);
            default:
                TodoItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.todo_item, parent, false);
                return new TodoItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                bindItemView(holder, position);
                break;
            case FOOTER:
                bindFooterView(holder, position);
                break;
        }
    }

    private void bindFooterView(RecyclerView.ViewHolder holder, int position) {
        TodoFooterViewHolder viewHolder = (TodoFooterViewHolder) holder;
        mTodoListController.bindFooterAction(viewHolder);
    }

    private void bindItemView(RecyclerView.ViewHolder holder, int position) {
        TodoItemViewHolder viewHolder = (TodoItemViewHolder) holder;
        viewHolder.bind(mItems.get(position));
        mTodoListController.observeItemHeight(viewHolder.itemView);
        startAnimateItemView(viewHolder, position);
    }

    private void startAnimateItemView(TodoItemViewHolder viewHolder, int position) {
        animateItemView(viewHolder.itemView, position);
        animationEnd().observeOn(AndroidSchedulers.mainThread()).subscribe(done -> {
            if (done) mTodoListController.bindItemAction(viewHolder, mItems.get(position));
        });
    }

    public void resetState() {
        mTodoListController.resetState();
    }
}