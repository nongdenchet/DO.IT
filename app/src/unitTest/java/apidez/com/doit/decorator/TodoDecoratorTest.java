package apidez.com.doit.decorator;

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
import apidez.com.doit.R;
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
public class TodoDecoratorTest {
    private TodoDecorator mDecorator;

    @Mock
    private Todo mTodo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mDecorator = new TodoDecorator(mTodo);
    }

    @Test
    public void testGetTodo() throws Exception {
        assertEquals(mTodo, mDecorator.getTodo());
    }

    @Test
    public void testOpacityCompleted() throws Exception {
        when(mTodo.isCompleted()).thenReturn(true);
        assertEquals(0.5f, mDecorator.getOpacity());
    }

    @Test
    public void testOpacityInCompleted() throws Exception {
        when(mTodo.isCompleted()).thenReturn(false);
        assertEquals(1.0f, mDecorator.getOpacity());
    }

    @Test
    public void testOpacityEnable() throws Exception {
        mDecorator.setCompleted(false);
        assertEquals(1.0f, mDecorator.getOpacity());
    }

    @Test
    public void testIsComplete() throws Exception {
        when(mTodo.isCompleted()).thenReturn(true);
        assertTrue(mDecorator.isCompleted());
    }

    @Test
    public void testOpacityDefault() throws Exception {
        assertEquals(1.0f, mDecorator.getOpacity());
    }

    @Test
    public void testOpacityDisable() throws Exception {
        mDecorator.setCompleted(false);
        assertEquals(1.0f, mDecorator.getOpacity());
    }

    @Test
    public void testUpdateCheck() throws Exception {
        mDecorator.setCompleted(true);
        assertFalse(mDecorator.getEnableState().get());
    }

    @Test
    public void testGetTitle() throws Exception {
        when(mTodo.getTitle()).thenReturn("title");
        assertEquals("title", mDecorator.getTitle());
    }

    @Test
    public void testGetDueDate() throws Exception {
        Date date = new Date(1454994901327L);
        when(mTodo.getDueDate()).thenReturn(date);
        assertEquals("09/02/2016", mDecorator.getDueDate());
    }

    @Test
    public void testNoDueDate() throws Exception {
        when(mTodo.getDueDate()).thenReturn(null);
        assertEquals("No due date", mDecorator.getDueDate());
    }

    @Test
    public void testGetPriorityHigh() throws Exception {
        when(mTodo.getPriority()).thenReturn(Priority.HIGH);
        assertEquals("HIGH", mDecorator.getPriority());
    }

    @Test
    public void testGetPriorityMediumColor() throws Exception {
        when(mTodo.getPriority()).thenReturn(Priority.MED);
        assertEquals(R.color.bg_priority_medium, mDecorator.getPriorityColor());
    }

    @Test
    public void testGetPriorityHighColor() throws Exception {
        when(mTodo.getPriority()).thenReturn(Priority.HIGH);
        assertEquals(R.color.bg_priority_high, mDecorator.getPriorityColor());
    }

    @Test
    public void testGetPriorityLowColor() throws Exception {
        when(mTodo.getPriority()).thenReturn(Priority.LOW);
        assertEquals(R.color.bg_priority_low, mDecorator.getPriorityColor());
    }

    @Test
    public void testGetPriorityLowTitleColor() throws Exception {
        when(mTodo.getPriority()).thenReturn(Priority.LOW);
        assertEquals(android.R.color.black, mDecorator.getPriorityTitleColor());
    }

    @Test
    public void testGetPriorityMediumTitleColor() throws Exception {
        when(mTodo.getPriority()).thenReturn(Priority.MED);
        assertEquals(android.R.color.black, mDecorator.getPriorityTitleColor());
    }

    @Test
    public void testGetPriorityHighTitleColor() throws Exception {
        when(mTodo.getPriority()).thenReturn(Priority.HIGH);
        assertEquals(android.R.color.white, mDecorator.getPriorityTitleColor());
    }
}