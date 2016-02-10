package apidez.com.doit.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import apidez.com.doit.R;
import apidez.com.doit.utils.UiUtils;
import apidez.com.doit.view.fragment.TodoListFragment;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        observeContentSize();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, TodoListFragment.newInstance())
                .commit();
    }

    private void observeContentSize() {
        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                UiUtils.CONTENT_HEIGHT = content.getHeight();
            }
        });
    }
}
