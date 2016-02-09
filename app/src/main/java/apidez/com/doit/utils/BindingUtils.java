package apidez.com.doit.utils;

import android.databinding.BindingAdapter;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import apidez.com.doit.view.adapter.BaseRecyclerViewAdapter;

/**
 * Created by nongdenchet on 1/5/16.
 */
public class BindingUtils {

    @BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView, List<T> items) {
        BaseRecyclerViewAdapter<T> adapter = (BaseRecyclerViewAdapter<T>) recyclerView.getAdapter();
        if (adapter != null) adapter.setItems(items);
    }

    @BindingAdapter({"android:src"})
    public static void src(ImageView imageView, int resource) {
        final int sdk = Build.VERSION.SDK_INT;
        imageView.setBackgroundResource(resource);
    }
}