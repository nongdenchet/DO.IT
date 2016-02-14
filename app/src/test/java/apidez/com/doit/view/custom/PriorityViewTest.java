package apidez.com.doit.view.custom;

import android.view.View;
import android.widget.TextView;

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
import de.hdodenhof.circleimageview.CircleImageView;

import static apidez.com.doit.AssertUtils.assertImageRes;
import static apidez.com.doit.AssertUtils.assertTextViewColor;
import static apidez.com.doit.AssertUtils.assertTextViewContent;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by nongdenchet on 2/14/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class PriorityViewTest {
    private PriorityView mPriorityView;
    private TextView mTitle;
    private CircleImageView mBackgroundDisable;
    private CircleImageView mBackground;

    @Before
    public void setUp() throws Exception {
        mPriorityView = new PriorityView(RuntimeEnvironment.application, null);
        initialize();
    }

    private void initialize() {
        mTitle = (TextView) mPriorityView.findViewById(R.id.title);
        mBackground = (CircleImageView) mPriorityView.findViewById(R.id.background);
        mBackgroundDisable = (CircleImageView) mPriorityView.findViewById(R.id.background_disable);
    }

    @Test
    public void testInflateLayout() throws Exception {
        assertNotNull(mTitle);
        assertNotNull(mBackground);
        assertNotNull(mBackgroundDisable);
    }

    @Test
    public void testSetPriorityHigh() throws Exception {
        mPriorityView.setPriority(Priority.HIGH);
        assertTextViewContent(mTitle, "HIGH");
        assertImageRes(mBackground, R.color.bg_priority_high);
        assertTextViewColor(mTitle, android.R.color.white);
        assertEquals(Priority.HIGH, mPriorityView.getPriority());
    }

    @Test
    public void testSetPriorityMedium() throws Exception {
        mPriorityView.setPriority(Priority.MED);
        assertTextViewContent(mTitle, "MED");
        assertImageRes(mBackground, R.color.bg_priority_medium);
        assertTextViewColor(mTitle, android.R.color.black);
        assertEquals(Priority.MED, mPriorityView.getPriority());
    }

    @Test
    public void testSetPriorityLow() throws Exception {
        mPriorityView.setPriority(Priority.LOW);
        assertTextViewContent(mTitle, "LOW");
        assertImageRes(mBackground, R.color.bg_priority_low);
        assertTextViewColor(mTitle, android.R.color.black);
        assertEquals(Priority.LOW, mPriorityView.getPriority());
    }

    @Test
    public void testSelect() throws Exception {
        mPriorityView.setPriority(Priority.HIGH);
        mPriorityView.select();
        assertTextViewColor(mTitle, android.R.color.white);
        assertEquals(View.VISIBLE, mBackground.getVisibility());
        assertEquals(View.GONE, mBackgroundDisable.getVisibility());
    }

    @Test
    public void testUnSelect() throws Exception {
        mPriorityView.setPriority(Priority.HIGH);
        mPriorityView.unSelect();
        assertTextViewColor(mTitle, android.R.color.black);
        assertEquals(View.GONE, mBackground.getVisibility());
        assertEquals(View.VISIBLE, mBackgroundDisable.getVisibility());
    }
}