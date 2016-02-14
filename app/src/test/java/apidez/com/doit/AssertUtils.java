package apidez.com.doit;

import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.robolectric.shadows.ShadowDrawable;
import org.robolectric.shadows.ShadowView;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by nongdenchet on 2/14/16.
 */
public class AssertUtils {

    /**
     * Helper that assert image resource of ImageView
     */
    public static void assertImageRes(ImageView imageView, int res) throws NoSuchFieldException, IllegalAccessException {
        ShadowDrawable shadowDrawable = shadowOf(imageView.getDrawable());
        assertEquals(res, shadowDrawable.getCreatedFromResId());
    }

    /**
     * Helper that assert content of TextView
     */
    public static void assertTextViewContent(TextView textView, String content) {
        assertEquals(content, textView.getText().toString());
    }

    /**
     * Helper that assert content color of TextView
     */
    public static void assertTextViewColor(TextView textView, int color) {
        assertEquals(ColorStateList.valueOf(ContextCompat.getColor(textView.getContext(), color)), textView.getTextColors());
    }

    /**
     * Helper that assert content color of TextView
     */
    public static void assertViewBackgroundColor(View view, int color) {
        ShadowView shadowView = shadowOf(view);
        assertEquals(ContextCompat.getColor(view.getContext(), color),
                shadowView.getBackgroundColor());
    }
}
