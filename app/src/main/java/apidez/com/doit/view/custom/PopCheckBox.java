package apidez.com.doit.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class PopCheckBox extends RelativeLayout {


    public PopCheckBox(Context context) {
        super(context);
    }

    public PopCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PopCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
