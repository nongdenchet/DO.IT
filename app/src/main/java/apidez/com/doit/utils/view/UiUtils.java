package apidez.com.doit.utils.view;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by nongdenchet on 2/9/16.
 */
public class UiUtils {
    public static int CONTENT_HEIGHT;

    private UiUtils() {
    }

    public static int getScreenHeight(Context context) {
        return getSize(context).y;
    }

    public static int getScreenWidth(Context context) {
        return getSize(context).x;
    }

    private static Point getSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static int getContentHeightWithoutToolbar(Context context) {
        return getContentHeight(context) - getToolbarHeight(context);
    }

    public static int getContentHeight(Context context) {
        return CONTENT_HEIGHT == 0 ? getScreenHeight(context) : CONTENT_HEIGHT;
    }

    public static int getToolbarHeight(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        }
        return 0;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
