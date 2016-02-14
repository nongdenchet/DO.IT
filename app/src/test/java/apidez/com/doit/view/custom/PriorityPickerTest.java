package apidez.com.doit.view.custom;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.R;
import apidez.com.doit.model.Priority;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by nongdenchet on 2/14/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class PriorityPickerTest {
    private PriorityPicker mPriorityPicker;
    private TestSubscriber mTestSubscriber = TestSubscriber.create();
    private PriorityView mHighPriorityView, mMediumPriorityView, mLowPriorityView;

    @Before
    public void setUp() throws Exception {
        mPriorityPicker = new PriorityPicker(RuntimeEnvironment.application);
        mHighPriorityView = (PriorityView) mPriorityPicker.findViewById(R.id.priority_high);
        mMediumPriorityView = (PriorityView) mPriorityPicker.findViewById(R.id.priority_medium);
        mLowPriorityView = (PriorityView) mPriorityPicker.findViewById(R.id.priority_low);
    }

    @Test
    public void testConstructor() throws Exception {
        assertNotNull(new PriorityPicker(RuntimeEnvironment.application, null));
        assertNotNull(new PriorityPicker(RuntimeEnvironment.application, null, 0));
    }

    @Test
    public void testInflateView() throws Exception {
        assertNotNull(mLowPriorityView);
        assertNotNull(mHighPriorityView);
        assertNotNull(mMediumPriorityView);
    }

    @Test
    public void testSetPriority() throws Exception {
        mPriorityPicker.priority().subscribe(mTestSubscriber);
        mPriorityPicker.setPriority(Priority.LOW);
        mTestSubscriber.assertValues(Priority.HIGH, Priority.LOW);
    }

    @Test
    public void testDefaultPriority() throws Exception {
        mPriorityPicker.priority().subscribe(mTestSubscriber);
        mTestSubscriber.assertValues(Priority.HIGH);
    }

    @Test
    public void testClickPriorityLow() throws Exception {
        mPriorityPicker.priority().subscribe(mTestSubscriber);
        mLowPriorityView.performClick();
        mTestSubscriber.assertValues(Priority.HIGH, Priority.LOW);
    }

    @Test
    public void testSelectPriorityLow() throws Exception {
        mLowPriorityView.performClick();
        assertSelected(mLowPriorityView);
        assertNotSelected(mHighPriorityView);
        assertNotSelected(mMediumPriorityView);
    }

    @Test
    public void testSelectPriorityMedium() throws Exception {
        mMediumPriorityView.performClick();
        assertSelected(mMediumPriorityView);
        assertNotSelected(mLowPriorityView);
        assertNotSelected(mHighPriorityView);
    }

    @Test
    public void testSelectPriorityHigh() throws Exception {
        mHighPriorityView.performClick();
        assertSelected(mHighPriorityView);
        assertNotSelected(mLowPriorityView);
        assertNotSelected(mMediumPriorityView);
    }

    @Test
    public void testClickPriorityMed() throws Exception {
        mPriorityPicker.priority().subscribe(mTestSubscriber);
        mMediumPriorityView.performClick();
        mTestSubscriber.assertValues(Priority.HIGH, Priority.MED);
    }

    @Test
    public void testClickPriorityHigh() throws Exception {
        mPriorityPicker.priority().subscribe(mTestSubscriber);
        mHighPriorityView.performClick();
        mTestSubscriber.assertValues(Priority.HIGH, Priority.HIGH);
    }

    private void assertSelected(PriorityView priorityView) {
        assertEquals(View.VISIBLE, priorityView.findViewById(R.id.background).getVisibility());
        assertEquals(View.GONE, priorityView.findViewById(R.id.background_disable).getVisibility());
    }

    private void assertNotSelected(PriorityView priorityView) {
        assertEquals(View.GONE, priorityView.findViewById(R.id.background).getVisibility());
        assertEquals(View.VISIBLE, priorityView.findViewById(R.id.background_disable).getVisibility());
    }
}