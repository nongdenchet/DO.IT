package apidez.com.doit.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.List;

import apidez.com.doit.R;
import apidez.com.doit.model.Todo;
import apidez.com.doit.utils.view.UiUtils;
import apidez.com.doit.view.adapter.viewholder.TodoFooterViewHolder;
import apidez.com.doit.view.adapter.viewholder.TodoItemViewHolder;
import apidez.com.doit.viewmodel.TodoItemViewModel;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 2/15/16.
 */
public class TodoListController {
    private View mFooter;
    private EventBus mEventBus;
    private Context mContext;

    private List<TodoItemViewModel> mItems;
    private BehaviorSubject<Integer> mListSize;
    private BehaviorSubject<Integer> mItemHeight;

    public interface CheckCallBack {
        void onCheckChange(boolean complete);
    }

    public TodoListController(@NonNull Context context, @NonNull List<TodoItemViewModel> items,
                              @NonNull EventBus eventBus) {
        this.mContext = context;
        this.mItems = items;
        this.mEventBus = eventBus;
        mListSize = BehaviorSubject.create(items.size());
        mItemHeight = BehaviorSubject.create(0);
    }

    public void setItems(List<TodoItemViewModel> items) {
        this.mItems = items;
        mListSize.onNext(items.size());
    }

    // Footer
    public void bindFooterAction(TodoFooterViewHolder holder) {
        mFooter = holder.itemView;
        mFooter.setOnClickListener(v -> resetState());
        footerHeight().subscribe(holder::setFooterHeight);
    }

    private Observable<Integer> footerHeight() {
        return Observable.combineLatest(mListSize, mItemHeight, this::calculateFooterHeight);
    }

    private int calculateFooterHeight(int listSize, int itemHeight) {
        if (listSize == 0) return 0;
        int contentHeight = UiUtils.getContentHeightWithoutToolbar(mContext);
        int contentListHeight = listSize * itemHeight;
        return contentHeight > contentListHeight ? (contentHeight - contentListHeight) : 0;
    }

    // Item
    public void bindItemAction(TodoItemViewHolder viewHolder, TodoItemViewModel viewModel) {
        viewHolder.todoView.setOnClickListener(v -> handleChooseItem(viewHolder.itemView, viewModel));
        viewHolder.disableLayer.setOnClickListener(v -> resetState());
        viewHolder.popCheckBox.setOnClickListener(v -> mEventBus.post(new CheckItemEvent(viewModel, viewHolder::animateCheckChange)));
        viewHolder.editButton.setOnClickListener(v -> mEventBus.post(new UpdateActionItemEvent(viewModel.getTodo())));
        viewHolder.deleteButton.setOnClickListener(v -> mEventBus.post(new DeleteActionItemEvent(indexOf(viewModel))));
    }

    public void observeItemHeight(View itemView) {
        itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mItemHeight.onNext(itemView.getHeight());
                itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
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
                mEventBus.post(new ShowActionItemEvent(indexOf(decorator)));
                itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private int indexOf(TodoItemViewModel decorator) {
        return mItems.indexOf(decorator);
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

    public class DeleteActionItemEvent {
        public int position;

        public DeleteActionItemEvent(int position) {
            this.position = position;
        }
    }

    public class ShowActionItemEvent {
        public int position;

        public ShowActionItemEvent(int position) {
            this.position = position;
        }
    }
}
