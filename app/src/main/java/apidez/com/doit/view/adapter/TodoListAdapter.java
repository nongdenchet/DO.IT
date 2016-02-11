package apidez.com.doit.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import apidez.com.doit.R;
import apidez.com.doit.databinding.TodoItemBinding;
import apidez.com.doit.decorator.TodoDecorator;
import apidez.com.doit.model.Todo;
import apidez.com.doit.view.viewholder.TodoItemViewHolder;
import de.greenrobot.event.EventBus;

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
        // Item click
        viewHolder.todoView.setOnClickListener(v -> handleChooseItem(viewHolder.itemView, decorator));

        // Disable layer click
        viewHolder.disableLayer.setOnClickListener(v -> resetState());

        // Checkbox click
        viewHolder.popCheckBox.setOnClickListener(v ->
                EventBus.getDefault().post(new CheckItemEvent(decorator.getTodo(), viewHolder::animateCheckChange)));

        // Update click
        viewHolder.editButton.setOnClickListener(v ->
                EventBus.getDefault().post(new UpdateActionItemEvent(indexOf(decorator), decorator.getTodo())));

        // Delete click
        viewHolder.deleteButton.setOnClickListener(v ->
                EventBus.getDefault().post(new DeleteActionItemEvent(indexOf(decorator))));
    }

    private void resetState() {
        for (TodoDecorator todoDecorator : mItems) {
            todoDecorator.resetState();
        }
    }

    private void handleChooseItem(View itemView, TodoDecorator decorator) {
        decorator.switchActionVisibility();
        for (TodoDecorator todoDecorator : mItems) {
            if (todoDecorator != decorator) {
                todoDecorator.switchEnableWhenNotChoose();
            }
        }
        itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                EventBus.getDefault().post(new ShowActionItemEvent(indexOf(decorator)));
                itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    // Callbacks
    public interface CheckCallBack {
        void onCheckChange(boolean complete);
    }

    // Events
    public class CheckItemEvent {
        public CheckCallBack callBack;
        public Todo todo;

        public CheckItemEvent(Todo todo, CheckCallBack callBack) {
            this.todo = todo;
            this.callBack = callBack;
        }
    }

    public class UpdateActionItemEvent extends ItemEvent {
        public Todo todo;

        public UpdateActionItemEvent(int position, Todo todo) {
            super(position);
            this.todo = todo;
        }
    }

    public class DeleteActionItemEvent extends ItemEvent {
        public DeleteActionItemEvent(int position) {
            super(position);
        }
    }

    public class ShowActionItemEvent extends ItemEvent {
        public ShowActionItemEvent(int position) {
            super(position);
        }
    }
}