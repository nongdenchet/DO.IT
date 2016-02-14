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
import apidez.com.doit.model.Todo;
import apidez.com.doit.utils.view.UiUtils;
import apidez.com.doit.view.adapter.viewholder.TodoFooterViewHolder;
import apidez.com.doit.view.adapter.viewholder.TodoItemViewHolder;
import apidez.com.doit.viewmodel.TodoItemViewModel;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListAdapter extends SlideInAnimationAdapter<TodoItemViewModel> {
    private final int ITEM = 0;
    private final int FOOTER = 1;
    private boolean isAnimate = true;
    private View mFooter;

    public TodoListAdapter(Context context) {
        super(context);
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
                bindFooter(holder);
                break;
        }
    }

    private void bindFooter(RecyclerView.ViewHolder holder) {
        TodoFooterViewHolder viewHolder = (TodoFooterViewHolder) holder;
        mFooter = holder.itemView;
        mFooter.setOnClickListener(v -> resetState());
        footerHeight().subscribe(viewHolder::setFooterHeight);
    }

    private Observable<Integer> footerHeight() {
        return Observable.combineLatest(listSize, itemHeight, this::calculateFooterHeight);
    }

    private int calculateFooterHeight(int listSize, int itemHeight) {
        if (listSize == 0) return 0;
        int contentHeight = UiUtils.getContentHeightWithoutToolbar(mContext);
        int contentListHeight = listSize * itemHeight;
        return contentHeight > contentListHeight ? (contentHeight - contentListHeight) : 0;
    }

    private void bindItemView(RecyclerView.ViewHolder holder, int position) {
        TodoItemViewHolder viewHolder = (TodoItemViewHolder) holder;
        viewHolder.bind(mItems.get(position));
        animateItem(viewHolder.itemView, position);
        animationEnd().observeOn(AndroidSchedulers.mainThread()).subscribe(done -> {
            if (done) bindAction(viewHolder, mItems.get(position));
        });
    }

    public void bindAction(TodoItemViewHolder viewHolder, TodoItemViewModel viewModel) {
        // Item click
        viewHolder.todoView.setOnClickListener(v -> handleChooseItem(viewHolder.itemView, viewModel));

        // Disable layer click
        viewHolder.disableLayer.setOnClickListener(v -> resetState());

        // Checkbox click
        viewHolder.popCheckBox.setOnClickListener(v ->
                EventBus.getDefault().post(new CheckItemEvent(viewModel, viewHolder::animateCheckChange)));

        // Update click
        viewHolder.editButton.setOnClickListener(v ->
                EventBus.getDefault().post(new UpdateActionItemEvent(viewModel.getTodo())));

        // Delete click
        viewHolder.deleteButton.setOnClickListener(v ->
                EventBus.getDefault().post(new DeleteActionItemEvent(indexOf(viewModel))));
    }

    public void resetState() {
        updateFooterWhenClickItem(false);
        for (TodoItemViewModel todoItemViewModel : mItems) {
            todoItemViewModel.resetState();
        }
    }

    private void handleChooseItem(View itemView, TodoItemViewModel decorator) {
        updateListWhenClickItem(decorator);
        updateFooterWhenClickItem(decorator.actionShowing());
        waitForLayoutCompleteFireEvent(itemView, decorator);
    }

    private void updateFooterWhenClickItem(boolean actionShowing) {
        if (mFooter != null) {
            mFooter.setBackgroundResource(actionShowing ? R.color.footer_disable : R.color.footer_enable);
        }
    }

    private void updateListWhenClickItem(TodoItemViewModel decorator) {
        decorator.switchActionVisibility();
        for (TodoItemViewModel todoItemViewModel : mItems) {
            if (todoItemViewModel != decorator) {
                todoItemViewModel.switchEnableWhenNotChoose();
            }
        }
    }

    private void waitForLayoutCompleteFireEvent(View itemView, TodoItemViewModel decorator) {
        itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                EventBus.getDefault().post(new ShowActionItemEvent(indexOf(decorator)));
                itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
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

    // Callbacks
    public interface CheckCallBack {
        void onCheckChange(boolean complete);
    }

    // Events
    public class CheckItemEvent {
        public CheckCallBack callBack;
        public TodoItemViewModel viewModel;

        public CheckItemEvent(TodoItemViewModel viewModel, CheckCallBack callBack) {
            this.viewModel = viewModel;
            this.callBack = callBack;
        }
    }

    public class UpdateActionItemEvent {
        public Todo todo;

        public UpdateActionItemEvent(Todo todo) {
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