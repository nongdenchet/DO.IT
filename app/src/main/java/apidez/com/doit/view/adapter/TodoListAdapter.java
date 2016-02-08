package apidez.com.doit.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apidez.com.doit.R;
import apidez.com.doit.decorator.TodoDecorator;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoListAdapter extends BaseRecyclerViewAdapter<TodoDecorator> {
    private Context mContext;

    public TodoListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(mContext).inflate(R.layout.todo_item, parent, false);
        return new TodoItemViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TodoItemViewHolder viewHolder = (TodoItemViewHolder) holder;
        viewHolder.itemView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TodoItemViewHolder extends RecyclerView.ViewHolder {

        public TodoItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
