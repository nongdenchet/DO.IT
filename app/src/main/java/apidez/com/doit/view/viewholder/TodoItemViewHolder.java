package apidez.com.doit.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import apidez.com.doit.R;
import apidez.com.doit.databinding.TodoItemBinding;
import apidez.com.doit.decorator.TodoDecorator;
import apidez.com.doit.utils.AnimationUtils;
import apidez.com.doit.view.custom.PopCheckBox;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by nongdenchet on 2/9/16.
 */
public class TodoItemViewHolder extends RecyclerView.ViewHolder {
    private final int ALPHA_ANIM_DURATION = 150;

    // Callbacks
    public interface CheckCallBack {
        void onCheckChange(boolean complete);
    }

    public TodoItemBinding binding;
    public TodoDecorator decorator;

    @InjectView(R.id.pop_checkbox)
    PopCheckBox popCheckBox;

    @InjectView(R.id.todo)
    View todoView;

    public TodoItemViewHolder(TodoItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        ButterKnife.inject(this, binding.getRoot());
    }

    public void bind(TodoDecorator decorator) {
        this.decorator = decorator;
        bindDecorator();
        bindAction();
    }

    private void bindDecorator() {
        binding.setDecorator(decorator);
        binding.executePendingBindings();
    }

    public void bindAction() {
        // Item click
        itemView.setOnClickListener(v -> {
            // TODO: implement this
        });

        // Check box
        popCheckBox.setOnClickListener(v -> EventBus.getDefault().post(
                new CheckItemEvent(decorator, this::animateCheck)));
    }

    private void animateCheck(boolean complete) {
        popCheckBox.animateChecked(complete);
        AnimationUtils.animateAlpha(todoView, decorator.getOpacity(), ALPHA_ANIM_DURATION);
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
