package apidez.com.doit.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import apidez.com.doit.R;
import apidez.com.doit.databinding.TodoItemBinding;
import apidez.com.doit.decorator.TodoDecorator;
import apidez.com.doit.view.custom.PopCheckBox;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListAdapter extends BaseRecyclerViewAdapter<TodoDecorator> {
    private Context mContext;

    public TodoListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TodoItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.todo_item, parent, false);
        return new TodoItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TodoItemViewHolder viewHolder = (TodoItemViewHolder) holder;
        viewHolder.bindDecorator(mItems.get(position));
        viewHolder.bindAction(mItems.get(position));
    }

    public class TodoItemViewHolder extends RecyclerView.ViewHolder {
        public TodoItemBinding binding;

        @InjectView(R.id.pop_checkbox)
        PopCheckBox popCheckBox;

        public TodoItemViewHolder(TodoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.inject(this, binding.getRoot());
        }

        public void bindDecorator(TodoDecorator decorator) {
            binding.setDecorator(decorator);
            binding.executePendingBindings();
        }

        public void bindAction(TodoDecorator decorator) {
            // Item click
            itemView.setOnClickListener(v -> {
                // TODO: implement this
            });

            // Check box
            popCheckBox.setOnClickListener(v -> EventBus.getDefault().post(new CheckItemEvent(indexOf(decorator),
                    complete -> {
                        decorator.updateCheck(complete);
                        popCheckBox.animateChecked(complete);
                    })));
        }
    }

    public interface CheckCallBack {
        void onCheckChange(boolean complete);
    }

    // Events
    public class CheckItemEvent extends ItemEvent {
        public CheckCallBack callBack;

        public CheckItemEvent(int position, CheckCallBack callBack) {
            super(position);
            this.callBack = callBack;
        }
    }
}
