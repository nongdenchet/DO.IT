package apidez.com.doit.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import apidez.com.doit.R;
import apidez.com.doit.databinding.TodoItemBinding;
import apidez.com.doit.decorator.TodoDecorator;
import apidez.com.doit.view.viewholder.TodoItemViewHolder;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListAdapter extends SlideInAnimationAdapter<TodoDecorator> {
    private boolean isAnimate = true;

    public TodoListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TodoItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.todo_item, parent, false);
        return new TodoItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TodoItemViewHolder viewHolder = (TodoItemViewHolder) holder;
        viewHolder.bind(mItems.get(position));
        bindAction(viewHolder, mItems.get(position));
        animateItem(viewHolder.itemView, position);
    }

    public void bindAction(TodoItemViewHolder viewHolder, TodoDecorator decorator) {
        // click item
        viewHolder.itemView.setOnClickListener(v -> {
            // TODO: implement this
        });

        // bind state
        decorator.checkChange()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(complete -> {
                    viewHolder.itemView.setEnabled(!complete);
                });

        // bind click checkbox
        viewHolder.popCheckBox.setOnClickListener(v -> EventBus.getDefault().post(
                new CheckItemEvent(decorator, viewHolder::animateCheck)));
    }

    // Callbacks
    public interface CheckCallBack {
        void onCheckChange(boolean complete);
    }

    // Events
    public class CheckItemEvent {
        public CheckCallBack callBack;
        public TodoDecorator decorator;

        public CheckItemEvent(TodoDecorator decorator, CheckCallBack callBack) {
            this.decorator = decorator;
            this.callBack = callBack;
        }
    }
}