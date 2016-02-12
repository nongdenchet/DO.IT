package apidez.com.doit.viewmodel;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 2/9/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class TodoItemViewModelTest {
    private TodoItemViewModel mViewModel;

    @Mock
    private Todo mTodo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mViewModel = new TodoItemViewModel(mTodo);
    }

    @Test
    public void testDefaultState() throws Exception {
        assertResetState();
    }

    @Test
    public void testSwitchEnableWhenNotChooseDisable() throws Exception {
        mViewModel.switchEnableWhenNotChoose();
        assertEquals(View.VISIBLE, mViewModel.getDisableVisibility().get());
        assertFalse(mViewModel.getEnableState().get());
    }

    @Test
    public void testSwitchEnableWhenNotChooseEnable() throws Exception {
        mViewModel.switchEnableWhenNotChoose();
        mViewModel.switchEnableWhenNotChoose();
        assertEquals(View.INVISIBLE, mViewModel.getDisableVisibility().get());
        assertTrue(mViewModel.getEnableState().get());
    }

    @Test
    public void testSwitchActionVisibilityEnable() throws Exception {
        mViewModel.switchActionVisibility();
        assertEquals(View.VISIBLE, mViewModel.getActionVisibility().get());
        assertEquals(View.INVISIBLE, mViewModel.getDividerVisibility().get());
    }

    @Test
    public void testSwitchActionVisibilityDisable() throws Exception {
        mViewModel.switchActionVisibility();
        mViewModel.switchActionVisibility();
        assertEquals(View.GONE, mViewModel.getActionVisibility().get());
        assertEquals(View.VISIBLE, mViewModel.getDividerVisibility().get());
    }

    @Test
    public void testResetStateFromDisable() throws Exception {
        mViewModel.switchEnableWhenNotChoose();
        mViewModel.resetState();
        assertResetState();
    }

    @Test
    public void testResetStateFromEnable() throws Exception {
        mViewModel.switchActionVisibility();
        mViewModel.resetState();
        assertResetState();
    }

    private void assertResetState() {
        assertEquals(View.INVISIBLE, mViewModel.getDisableVisibility().get());
        assertEquals(View.GONE, mViewModel.getActionVisibility().get());
        assertEquals(View.VISIBLE, mViewModel.getDividerVisibility().get());
        assertTrue(mViewModel.getEnableState().get());
    }

    @Test
    public void testGetTodo() throws Exception {
        assertEquals(mTodo, mViewModel.getTodo());
    }

    @Test
    public void testOpacityCompleted() throws Exception {
        when(mTodo.isCompleted()).thenReturn(true);
        assertEquals(0.5f, mViewModel.getOpacity());
    }

    @Test
    public void testOpacityInCompleted() throws Exception {
        when(mTodo.isCompleted()).thenReturn(false);
        assertEquals(1.0f, mViewModel.getOpacity());
    }

    @Test
    public void testIsComplete() throws Exception {
        when(mTodo.isCompleted()).thenReturn(true);
        assertTrue(mViewModel.isCompleted());
    }

    @Test
    public void testOpacityDefault() throws Exception {
        assertEquals(1.0f, mViewModel.getOpacity());
    }

    @Test
    public void testGetTitle() throws Exception {
        when(mTodo.getTitle()).thenReturn("title");
        assertEquals("title", mViewModel.getTitle());
    }

    @Test
    public void testGetDueDate() throws Exception {
        Date date = new Date(1454994901327L);
        when(mTodo.getDueDate()).thenReturn(date);
        assertEquals("09/02/2016", mViewModel.getDueDate());
    }

    @Test
    public void testNoDueDate() throws Exception {
        when(mTodo.getDueDate()).thenReturn(null);
        assertEquals("No due date", mViewModel.getDueDate());
    }

    @Test
    public void testGetPriorityHigh() throws Exception {
        when(mTodo.getPriority()).thenReturn(Priority.HIGH);
        assertEquals(Priority.HIGH, mViewModel.getPriority());
    }

//    @Test
//    public void testGetPriorityMediumColor() throws Exception {
//        when(mTodo.getPriority()).thenReturn(Priority.MED);
//        assertEquals(R.color.bg_priority_medium, mViewModel.getPriorityColor());
//    }
//
//    @Test
//    public void testGetPriorityHighColor() throws Exception {
//        when(mTodo.getPriority()).thenReturn(Priority.HIGH);
//        assertEquals(R.color.bg_priority_high, mViewModel.getPriorityColor());
//    }
//
//    @Test
//    public void testGetPriorityLowColor() throws Exception {
//        when(mTodo.getPriority()).thenReturn(Priority.LOW);
//        assertEquals(R.color.bg_priority_low, mViewModel.getPriorityColor());
//    }
//
//    @Test
//    public void testGetPriorityLowTitleColor() throws Exception {
//        when(mTodo.getPriority()).thenReturn(Priority.LOW);
//        assertEquals(android.R.color.black, mViewModel.getPriorityTitleColor());
//    }
//
//    @Test
//    public void testGetPriorityMediumTitleColor() throws Exception {
//        when(mTodo.getPriority()).thenReturn(Priority.MED);
//        assertEquals(android.R.color.black, mViewModel.getPriorityTitleColor());
//    }
//
//    @Test
//    public void testGetPriorityHighTitleColor() throws Exception {
//        when(mTodo.getPriority()).thenReturn(Priority.HIGH);
//        assertEquals(android.R.color.white, mViewModel.getPriorityTitleColor());
//    }
}