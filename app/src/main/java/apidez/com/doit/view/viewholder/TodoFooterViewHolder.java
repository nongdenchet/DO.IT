package apidez.com.doit.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nongdenchet on 2/13/16.
 */
public class TodoFooterViewHolder extends RecyclerView.ViewHolder {

    public TodoFooterViewHolder(View itemView) {
        super(itemView);
    }

    public void setFooterHeight(int footerHeight) {
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height = footerHeight;
        itemView.setLayoutParams(layoutParams);
    }
}
