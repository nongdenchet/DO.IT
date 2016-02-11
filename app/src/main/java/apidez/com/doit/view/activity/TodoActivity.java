package apidez.com.doit.view.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

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
        drawStatusBar();
        addFragmentTodoList();
    }

    private void addFragmentTodoList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, TodoListFragment.newInstance())
                .commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    private void observeContentSize() {
        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                UiUtils.CONTENT_HEIGHT = content.getHeight();
                content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
