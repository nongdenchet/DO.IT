package apidez.com.doit.view.custom;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import apidez.com.doit.BuildConfig;
import apidez.com.doit.DefaultConfig;
import apidez.com.doit.R;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 2/14/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class PopCheckBoxTest {
    private PopCheckBox mPopCheckBox;
    private ImageView mCheckBox, mCheckBoxFill;

    @Mock
    private ViewPropertyAnimator mMockAnimatorCheckBox;

    @Mock
    private ViewPropertyAnimator  mMockAnimatorCheckBoxFill;

    @Before
    public void setUp() throws Exception {
        mPopCheckBox = new PopCheckBox(RuntimeEnvironment.application);
        mCheckBox = (ImageView) mPopCheckBox.findViewById(R.id.checkbox);
        mCheckBoxFill = (ImageView) mPopCheckBox.findViewById(R.id.checkbox_fill);

        MockitoAnnotations.initMocks(this);
        Field field = View.class.getDeclaredField("mAnimator");
        field.setAccessible(true);
        setUpMock(mMockAnimatorCheckBoxFill);
        setUpMock(mMockAnimatorCheckBox);
        field.set(mCheckBox, mMockAnimatorCheckBox);
        field.set(mCheckBoxFill, mMockAnimatorCheckBoxFill);
    }

    @Test
    public void testConstructor() throws Exception {
        Assert.assertNotNull(new PopCheckBox(RuntimeEnvironment.application, null));
        Assert.assertNotNull(new PopCheckBox(RuntimeEnvironment.application, null, 0));
    }

    @Test
    public void testInitViews() throws Exception {
        assertNotNull(mCheckBox);
        assertNotNull(mCheckBoxFill);
    }

    @Test
    public void testSetCheckTrueCheckBoxFill() throws Exception {
        mPopCheckBox.setCheck(true);
        assertEquals(1.0f, mCheckBoxFill.getScaleX());
        assertEquals(1.0f, mCheckBoxFill.getScaleY());
    }

    @Test
    public void testSetCheckTrueCheckBox() throws Exception {
        mPopCheckBox.setCheck(true);
        assertEquals(0.0f, mCheckBox.getScaleX());
        assertEquals(0.0f, mCheckBox.getScaleY());
    }

    @Test
    public void testSetCheckFalseCheckBoxFill() throws Exception {
        mPopCheckBox.setCheck(false);
        assertEquals(0.0f, mCheckBoxFill.getScaleX());
        assertEquals(0.0f, mCheckBoxFill.getScaleY());
    }

    @Test
    public void testSetCheckFalseCheckBox() throws Exception {
        mPopCheckBox.setCheck(false);
        assertEquals(1.0f, mCheckBox.getScaleX());
        assertEquals(1.0f, mCheckBox.getScaleY());
    }

    @Test
    public void testAnimateCheckedNoAnimation() throws Exception {
        mPopCheckBox.animateChecked(false);
        verify(mMockAnimatorCheckBox, never()).start();
        verify(mMockAnimatorCheckBoxFill, never()).start();
    }

    @Test
    public void testAnimateCheckedTrue() throws Exception {
        mPopCheckBox.animateChecked(true);
        verifyCallAnimation(mMockAnimatorCheckBox, 0, 0.0f);
        verifyCallAnimation(mMockAnimatorCheckBoxFill, 75, 1.0f);
    }

    @Test
    public void testAnimateCheckedFalse() throws Exception {
        mPopCheckBox.setCheck(true);
        mPopCheckBox.animateChecked(false);
        verifyCallAnimation(mMockAnimatorCheckBox, 75, 1.0f);
        verifyCallAnimation(mMockAnimatorCheckBoxFill, 0, 0.0f);
    }

    private void setUpMock(ViewPropertyAnimator animator) {
        when(animator.setInterpolator(any())).thenReturn(animator);
        when(animator.setStartDelay(anyInt())).thenReturn(animator);
        when(animator.setDuration(anyInt())).thenReturn(animator);
        when(animator.scaleX(anyFloat())).thenReturn(animator);
        when(animator.scaleY(anyFloat())).thenReturn(animator);
    }

    private void verifyCallAnimation(ViewPropertyAnimator animator, int delay, float scale) {
        verify(animator).setInterpolator(any(AccelerateInterpolator.class));
        verify(animator).setDuration(75);
        verify(animator).setStartDelay(delay);
        verify(animator).scaleX(scale);
        verify(animator).scaleY(scale);
    }
}