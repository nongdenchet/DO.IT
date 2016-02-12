package apidez.com.doit.view.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import apidez.com.doit.R;
import apidez.com.doit.view.custom.DueDatePicker;
import apidez.com.domain.model.Todo;
import butterknife.InjectView;

/**
 * Created by nongdenchet on 2/11/16.
 */
public class TodoDialogFragment extends BaseDialogFragment implements DueDatePicker.ListenerPickDate {
    public static final String TAG = TodoDialogFragment.class.getSimpleName();
    private static final String TODO = "todo";

    @InjectView(R.id.due_date_picker)
    DueDatePicker mDueDatePicker;

    @InjectView(R.id.discard)
    TextView mDiscardButton;

    @InjectView(R.id.save)
    TextView mSaveButton;

    public static TodoDialogFragment newInstance() {
        return new TodoDialogFragment();
    }

    public static TodoDialogFragment newInstance(Todo todo) {
        Bundle args = new Bundle();
        args.putSerializable(TODO, todo);
        TodoDialogFragment fragment = new TodoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Todo todo = (Todo) getArguments().getSerializable(TODO);
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
