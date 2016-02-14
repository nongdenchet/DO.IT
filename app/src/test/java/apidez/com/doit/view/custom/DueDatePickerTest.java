package apidez.com.doit.view.custom;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.R;
import apidez.com.doit.utils.DateUtils;
import rx.observers.TestSubscriber;

import static apidez.com.doit.AssertUtils.assertViewBackgroundColor;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by nongdenchet on 2/14/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DueDatePickerTest {
    private DueDateView mTodayView, mTomorrowView, mDayPicker, mNoDateView;
    private DueDatePicker mDueDatePicker;
    private TestSubscriber mTestSubscriber = TestSubscriber.create();
    private DueDateView[] mDueDateViews;

    @Mock
    private DueDatePicker.ListenerPickDate mListenerPickDate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mDueDatePicker = new DueDatePicker(RuntimeEnvironment.application);
        mockPickerListener();

        mTodayView = (DueDateView) mDueDatePicker.findViewById(R.id.today);
        mTomorrowView = (DueDateView) mDueDatePicker.findViewById(R.id.tomorrow);
        mDayPicker = (DueDateView) mDueDatePicker.findViewById(R.id.date_picker);
        mNoDateView = (DueDateView) mDueDatePicker.findViewById(R.id.no_due_date);
        mDueDateViews = new DueDateView[]{mTodayView, mTomorrowView, mDayPicker, mNoDateView};
    }

    @Test
    public void testConstructor() throws Exception {
        assertNotNull(new DueDatePicker(RuntimeEnvironment.application, null));
        assertNotNull(new DueDatePicker(RuntimeEnvironment.application, null, 0));
    }

    @Test
    public void testInfalteLayout() throws Exception {
        assertNotNull(mTodayView);
        assertNotNull(mTomorrowView);
        assertNotNull(mDayPicker);
        assertNotNull(mNoDateView);
    }

    @Test
    public void testDefaultDate() throws Exception {
        mDueDatePicker.date().subscribe(date -> {
            assertTrue(DateUtils.isToday(date));
        });
    }

    @Test
    public void testSetDueDate() throws Exception {
        mDueDatePicker.setDueDate(DateUtils.createPastDate());
        mDueDatePicker.date().subscribe(date -> {
            assertEquals("10/10/2000", DateUtils.formatDate(date));
        });
        assertDueDateSelected(mDayPicker);
    }

    @Test
    public void testSetNoDueDate() throws Exception {
        mDueDatePicker.setDueDate(null);
        mDueDatePicker.date().subscribe(Assert::assertNull);
        assertDueDateSelected(mNoDateView);
    }

    @Test
    public void testSetDueDateTomorrow() throws Exception {
        mDueDatePicker.setDueDate(DateUtils.getTomorrow());
        mDueDatePicker.date().subscribe(date -> {
            assertTrue(DateUtils.isTomorrow(date));
        });
        assertDueDateSelected(mTomorrowView);
    }

    @Test
    public void testSetDueDateToday() throws Exception {
        mDueDatePicker.setDueDate(DateUtils.createRandomDate());
        mDueDatePicker.setDueDate(new Date());
        mDueDatePicker.date().subscribe(date -> {
            assertTrue(DateUtils.isToday(date));
        });
        assertDueDateSelected(mTodayView);
    }

    @Test
    public void testClickNoDueDate() throws Exception {
        mNoDateView.performClick();
        mDueDatePicker.date().subscribe(Assert::assertNull);
        assertDueDateSelected(mNoDateView);
    }

    @Test
    public void testClickDueDateTomorrow() throws Exception {
        mTomorrowView.performClick();
        mDueDatePicker.date().subscribe(date -> {
            assertTrue(DateUtils.isTomorrow(date));
        });
        assertDueDateSelected(mTomorrowView);
    }

    @Test
    public void testClickDueDateToday() throws Exception {
        mDueDatePicker.setDueDate(DateUtils.createRandomDate());
        mTodayView.performClick();
        mDueDatePicker.date().subscribe(date -> {
            assertTrue(DateUtils.isToday(date));
        });
        assertDueDateSelected(mTodayView);
    }

    @Test
    public void testClickDueDate() throws Exception {
        mDayPicker.performClick();
        mDueDatePicker.date().subscribe(date -> {
            assertEquals("14/02/2016", DateUtils.formatDate(date));
        });
        assertDueDateSelected(mDayPicker);
    }

    @Test
    public void testClickDueDateNoListener() throws Exception {
        mDueDatePicker.setListenerPickDate(null);
        mDayPicker.performClick();
        assertViewBackgroundColor(mDayPicker.findViewById(R.id.content), R.color.due_date_disable);
    }

    private void mockPickerListener() {
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((DueDatePicker.CallbackPickDate) args[0]).onDatePicked(2016, 1, 14);
            return null;
        }).when(mListenerPickDate).pickDate(any(DueDatePicker.CallbackPickDate.class));
        mDueDatePicker.setListenerPickDate(mListenerPickDate);
    }

    private void assertDueDateSelected(DueDateView dueDateView) {
        for (DueDateView view : mDueDateViews) {
            if (view == dueDateView) {
                assertViewBackgroundColor(view.findViewById(R.id.content), R.color.due_date_enable);
            } else {
                assertViewBackgroundColor(view.findViewById(R.id.content), R.color.due_date_disable);
            }
        }
    }
}