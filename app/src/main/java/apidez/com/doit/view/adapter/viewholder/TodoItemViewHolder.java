package apidez.com.doit.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import apidez.com.doit.R;
import apidez.com.doit.databinding.TodoItemBinding;
import apidez.com.doit.viewmodel.TodoItemViewModel;
import apidez.com.doit.utils.view.AnimationUtils;
import apidez.com.doit.view.custom.PopCheckBox;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nongdenchet on 2/9/16.
 */
public class TodoItemViewHolder extends RecyclerView.ViewHolder {
    private final int ALPHA_ANIM_DURATION = 150;
    public TodoItemBinding binding;
    public TodoItemViewModel decorator;

    @InjectView(R.id.pop_checkbox)
    public PopCheckBox popCheckBox;

    @InjectView(R.id.todo)
    public View todoView;

    @InjectView(R.id.edit_button)
    public View editButton;

    @InjectView(R.id.delete_button)
    public View deleteButton;

    @InjectView(R.id.disable_layer)
    public View disableLayer;

    public TodoItemViewHolder(TodoItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        ButterKnife.inject(this, binding.getRoot());
    }

    public void bind(TodoItemViewModel viewModel) {
        this.decorator = viewModel;
        binding.setDecorator(viewModel);
        binding.executePendingBindings();
    }

    public void animateCheckChange(boolean complete) {
        popCheckBox.animateChecked(complete);
        AnimationUtils.animateAlpha(todoView,
                decorator.getOpacity(), ALPHA_ANIM_DURATION);
    }
}
