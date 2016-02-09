package apidez.com.doit.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import apidez.com.doit.R;
import apidez.com.doit.view.fragment.TodoListFragment;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class TodoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, TodoListFragment.newInstance())
                .commit();
    }
}
