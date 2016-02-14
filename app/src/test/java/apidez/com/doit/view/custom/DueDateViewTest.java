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
import apidez.com.doit.utils.DateUtils;

import static apidez.com.doit.AssertUtils.assertTextViewContent;
import static apidez.com.doit.AssertUtils.assertViewBackgroundColor;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by nongdenchet on 2/14/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DueDateViewTest {
    private DueDateView mDueDateView;
    private TextView mDayTitle, mSubtitle;
    private View mBackground;

    @Before
    public void setUp() throws Exception {
        mDueDateView = new DueDateView(RuntimeEnvironment.application, null);
        mDayTitle = (TextView) mDueDateView.findViewById(R.id.dayTitle);
        mSubtitle = (TextView) mDueDateView.findViewById(R.id.subtitle);
        mBackground = mDueDateView.findViewById(R.id.content);
    }

    @Test
    public void testInfalteLayout() {
        assertNotNull(mDayTitle);
        assertNotNull(mSubtitle);
        assertNotNull(mBackground);
    }

    @Test
    public void testSelectTrue() throws Exception {
        mDueDateView.select(true);
        assertViewBackgroundColor(mBackground, R.color.due_date_enable);
    }

    @Test
    public void testSelectFalse() throws Exception {
        mDueDateView.select(false);
        assertViewBackgroundColor(mBackground, R.color.due_date_disable);
    }

    @Test
    public void testGetDateDefault() throws Exception {
        assertNull(mDueDateView.getDate());
    }

    @Test
    public void testSetInitDate() throws Exception {
        mDueDateView.setInitDate(DateUtils.fakeDate());
        assertTextViewContent(mDayTitle, "14");
    }

    @Test
    public void testSetPickDate() throws Exception {
        mDueDateView.setPickDate(2000, 2, 2);
        assertTextViewContent(mDayTitle, "02");
        assertTextViewContent(mSubtitle, "03/2000");
    }

    @Test
    public void testSetDayTitle() throws Exception {
        mDueDateView.setDayTitle("title");
        assertTextViewContent(mDayTitle, "title");
    }

    @Test
    public void testSetSubtitle() throws Exception {
        mDueDateView.setSubtitle("subtitle");
        assertTextViewContent(mSubtitle, "subtitle");
    }
}