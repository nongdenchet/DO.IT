package apidez.com.doit.view.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import apidez.com.doit.R;
import apidez.com.doit.model.Todo;
import apidez.com.doit.view.custom.DueDatePicker;
import butterknife.InjectView;

/**
 * Created by nongdenchet on 2/11/16.
 */
public class TodoDialogFragment extends BaseDialogFragment implements DueDatePicker.ListenerPickDate {
    public static final String TAG = TodoDialogFragment.class.getSimpleName();

    @InjectView(R.id.due_date_picker)
    DueDatePicker mDueDatePicker;

    @InjectView(R.id.discard)
    TextView mDiscardButton;

    @InjectView(R.id.save)
    TextView mSaveButton;

    public static TodoDialogFragment newInstance() {
        Bundle args = new Bundle();
        TodoDialogFragment fragment = new TodoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TodoDialogFragment newInstance(Todo todo) {
        Bundle args = new Bundle();
        TodoDialogFragment fragment = new TodoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onSetUpView(View rootView) {
        mDueDatePicker.setListenerPickDate(this);
        mDiscardButton.setOnClickListener(v -> {});
        mSaveButton.setOnClickListener(v -> {});
    }

    @Override
    protected int layout() {
        return R.layout.fragment_dialog_todo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void pickDate(DueDatePicker.CallbackPickDate callbackPickDate) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> callbackPickDate.onDatePicked(year, monthOfYear, dayOfMonth),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }
}
