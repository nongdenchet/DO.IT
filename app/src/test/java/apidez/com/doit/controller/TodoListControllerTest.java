package apidez.com.doit.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.R;
import apidez.com.doit.databinding.TodoItemBinding;
import apidez.com.doit.utils.DataUtils;
import apidez.com.doit.utils.view.UiUtils;
import apidez.com.doit.view.adapter.viewholder.TodoFooterViewHolder;
import apidez.com.doit.view.adapter.viewholder.TodoItemViewHolder;
import apidez.com.doit.view.custom.PopCheckBox;
import apidez.com.doit.viewmodel.TodoItemViewModel;
import de.greenrobot.event.EventBus;

import static apidez.com.doit.AssertUtils.assertViewBackgroundRes;
import static apidez.com.doit.AssertUtils.clickView;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 2/15/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({UiUtils.class})
public class TodoListControllerTest {
    private static final int CONTENT_HEIGHT = 1700;
    private static final int ITEM_HEIGHT = 230;

    private TodoListController mTodoListController;
    private List<TodoItemViewModel> mItemViewModelList;
    private Context mContext;

    private TodoItemViewHolder mTodoItemViewHolder;
    private TodoFooterViewHolder mTodoFooterViewHolder;
    private View mFooterView;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Mock
    private EventBus mEventBus;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        initController();
        initFooterView();
        initItemView();
        initMock();
    }

    private void initController() {
        mItemViewModelList = new ArrayList<>();
        mItemViewModelList.addAll(DataUtils.provideListItemViewModel());
        mContext = RuntimeEnvironment.application;
        mTodoListController = spy(new TodoListController(mContext, mItemViewModelList, mEventBus));
    }

    private void initItemView() {
        mTodoItemViewHolder = mockItemViewHolder();
        mTodoListController.bindItemAction(mTodoItemViewHolder, mItemViewModelList.get(0));
    }

    private void initFooterView() {
        mFooterView = mockFooterView();
        mTodoListController.bindFooterAction(new TodoFooterViewHolder(mFooterView));
    }

    private void initMock() {
        PowerMockito.mockStatic(UiUtils.class);
        PowerMockito.when(UiUtils.getContentHeightWithoutToolbar(mContext)).thenReturn(CONTENT_HEIGHT);
    }

    @Test
    public void testObserveHeightExist() throws Exception {
        mTodoListController.observeItemHeight(mFooterView);
        assertEquals(1010, mFooterView.getLayoutParams().height);
    }

    @Test
    public void testObserveHeightExistOnlyFirstTime() throws Exception {
        mTodoListController.observeItemHeight(mFooterView);
        when(mFooterView.getHeight()).thenReturn(100);
        mTodoListController.observeItemHeight(mFooterView);
        assertEquals(1010, mFooterView.getLayoutParams().height);
    }

    @Test
    public void testObserveHeightNonExist() throws Exception {
        mTodoListController.setItems(new ArrayList<>());
        mTodoListController.observeItemHeight(mFooterView);
        assertEquals(0, mFooterView.getLayoutParams().height);
    }

    @Test
    public void testObserveHeightLongList() throws Exception {
        mTodoListController.setItems(new ArrayList<>(DataUtils.provideLongListItemViewModel()));
        mTodoListController.observeItemHeight(mFooterView);
        assertEquals(0, mFooterView.getLayoutParams().height);
    }

    @Test
    public void testClickFooter() throws Exception {
        clickView(mFooterView);
        verify(mTodoListController).resetState();
    }

    @Test
    public void testObserveItemHeight() throws Exception {
        mTodoListController.observeItemHeight(mockFooterView());
    }

    @Test
    public void testResetState() throws Exception {
        clickView(mTodoItemViewHolder.todoView);
        mTodoListController.resetState();
        for (int i = 0; i < mItemViewModelList.size(); i++) {
            assertNormalitem(i);
        }
    }

    @Test
    public void testHandleClickItemEnable() throws Exception {
        clickView(mTodoItemViewHolder.todoView);
        assertViewBackgroundRes(mFooterView, R.color.footer_disable);
        assertEnableItem(0);
        assertDisableItem(1);
        assertDisableItem(2);
    }

    @Test
    public void testNotThrowExceptionWhenFooterNull() throws Exception {
        Exception exception = null;
        try {
            Field field = TodoListController.class.getDeclaredField("mFooterView");
            field.setAccessible(true);
            field.set(mTodoListController, null);
            clickView(mTodoItemViewHolder.todoView);
        } catch (Exception ex) {
            exception = ex;
        }
        assertNull(exception);
    }

    @Test
    public void testHandleClickItemFireEvent() throws Exception {
        clickView(mTodoItemViewHolder.todoView);
        verify(mEventBus).post(any(TodoListController.ShowActionItemEvent.class));
    }

    @Test
    public void testHandleClickItemDisable() throws Exception {
        clickView(mTodoItemViewHolder.todoView);
        clickView(mTodoItemViewHolder.todoView);
        assertViewBackgroundRes(mFooterView, R.color.footer_enable);
        for (int i = 0; i < mItemViewModelList.size(); i++) {
            assertNormalitem(i);
        }
    }

    @Test
    public void testClickDisableLayer() throws Exception {
        clickView(mTodoItemViewHolder.disableLayer);
        verify(mTodoListController).resetState();
    }

    @Test
    public void testClickEditButton() throws Exception {
        clickView(mTodoItemViewHolder.editButton);
        verify(mEventBus).post(any(TodoListController.UpdateActionItemEvent.class));
    }

    @Test
    public void testClickCheckbox() throws Exception {
        clickView(mTodoItemViewHolder.popCheckBox);
        verify(mEventBus).post(any(TodoListController.CheckItemEvent.class));
    }

    @Test
    public void testClickDeleteButton() throws Exception {
        clickView(mTodoItemViewHolder.deleteButton);
        verify(mEventBus).post(any(TodoListController.DeleteActionItemEvent.class));
    }

    private void assertEnableItem(int position) {
        assertEquals(View.VISIBLE, mItemViewModelList.get(position).getActionVisibility().get());
    }

    private void assertDisableItem(int position) {
        assertFalse(mItemViewModelList.get(position).getEnableState().get());
    }

    private void assertNormalitem(int position) {
        assertEquals(View.GONE, mItemViewModelList.get(position).getActionVisibility().get());
        assertTrue(mItemViewModelList.get(position).getEnableState().get());
    }

    private TodoItemViewHolder mockItemViewHolder() {
        TodoItemBinding binding = mock(TodoItemBinding.class);
        when(binding.getRoot()).thenReturn(spy(new View(mContext)));
        TodoItemViewHolder viewHolder = spy(new TodoItemViewHolder(binding));
        viewHolder.todoView = new View(mContext);
        viewHolder.disableLayer = new View(mContext);
        viewHolder.popCheckBox = new PopCheckBox(mContext);
        viewHolder.editButton = new View(mContext);
        viewHolder.deleteButton = new View(mContext);

        // Mock onLayoutChange
        ViewTreeObserver viewTreeObserver = mock(ViewTreeObserver.class);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((ViewTreeObserver.OnGlobalLayoutListener) args[0]).onGlobalLayout();
            return null;
        }).when(viewTreeObserver).addOnGlobalLayoutListener(any(ViewTreeObserver.OnGlobalLayoutListener.class));
        when(viewHolder.itemView.getViewTreeObserver()).thenReturn(viewTreeObserver);
        return viewHolder;
    }

    private View mockFooterView() {
        View view = spy(LayoutInflater.from(mContext).inflate(R.layout.todo_item_footer, new LinearLayout(mContext), true));
        view.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        when(view.getHeight()).thenReturn(ITEM_HEIGHT);

        // Mock onLayoutChange
        ViewTreeObserver viewTreeObserver = mock(ViewTreeObserver.class);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((ViewTreeObserver.OnGlobalLayoutListener) args[0]).onGlobalLayout();
            return null;
        }).when(viewTreeObserver).addOnGlobalLayoutListener(any(ViewTreeObserver.OnGlobalLayoutListener.class));
        when(view.getViewTreeObserver()).thenReturn(viewTreeObserver);
        return view;
    }
}