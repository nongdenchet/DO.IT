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

/**
 * Created by nongdenchet on 2/9/16.
 */
public class TodoItemViewHolder extends RecyclerView.ViewHolder {
    private final int ALPHA_ANIM_DURATION = 150;
    public TodoItemBinding binding;
    public TodoDecorator decorator;

    @InjectView(R.id.pop_checkbox)
    public PopCheckBox popCheckBox;

    @InjectView(R.id.todo)
    public View todoView;

    public TodoItemViewHolder(TodoItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        ButterKnife.inject(this, binding.getRoot());
    }

    public void bind(TodoDecorator decorator) {
        this.decorator = decorator;
        binding.setDecorator(decorator);
        binding.executePendingBindings();
    }

    public void animateCheck(boolean complete) {
        popCheckBox.animateChecked(complete);
        AnimationUtils.animateAlpha(todoView, decorator.getOpacity(), ALPHA_ANIM_DURATION);
    }
}
