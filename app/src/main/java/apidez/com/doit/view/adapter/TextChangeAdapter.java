package apidez.com.doit.view.adapter;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by nongdenchet on 2/12/16.
 */
public abstract class TextChangeAdapter implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public abstract void onTextChanged(String text);
}
