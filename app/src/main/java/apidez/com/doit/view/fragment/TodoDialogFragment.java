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
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import javax.inject.Inject;

import apidez.com.doit.DoItApp;
import apidez.com.doit.R;
import apidez.com.doit.dependency.module.TodoListModule;
import apidez.com.doit.model.Todo;
import apidez.com.doit.view.adapter.TextChangeAdapter;
import apidez.com.doit.view.custom.DueDatePicker;
import apidez.com.doit.view.custom.PriorityPicker;
import apidez.com.doit.viewmodel.TodoDialogViewModel;
import butterknife.InjectView;

/**
 * Created by nongdenchet on 2/11/16.
 */
public class TodoDialogFragment extends BaseDialogFragment implements DueDatePicker.ListenerPickDate {
    public static final String TAG = TodoDialogFragment.class.getSimpleName();
    private static final String TODO = "todo";
    private boolean isRestore = false;
    private CallbackSuccess mCallbackSuccess;

    @InjectView(R.id.discard)
    TextView mDiscardButton;

    @InjectView(R.id.save)
    TextView mSaveButton;

    @InjectView(R.id.title_edit_text)
    EditText mTitleEditText;

    @InjectView(R.id.priority_picker)
    PriorityPicker mPriorityPicker;

    @InjectView(R.id.due_date_picker)
    DueDatePicker mDueDatePicker;

    @Inject
    TodoDialogViewModel mViewModel;

    public interface CallbackSuccess {
        void onCreateSuccess(Todo todo);
        void onUpdateSuccess(Todo todo);
    }

    public void setCallbackSuccess(CallbackSuccess callbackSuccess) {
        this.mCallbackSuccess = callbackSuccess;
    }

    public static TodoDialogFragment newInstance() {
        Bundle args = new Bundle();
        TodoDialogFragment fragment = new TodoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TodoDialogFragment newInstance(Todo todo) {
        Bundle args = new Bundle();
        args.putSerializable(TODO, todo);
        TodoDialogFragment fragment = new TodoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int layout() {
        return R.layout.fragment_dialog_todo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DoItApp.app().component()
                .plus(new TodoListModule())
                .inject(this);
    }

    @Override
    protected void onSetUpView(View rootView) {
        mDueDatePicker.setListenerPickDate(this);
        mDiscardButton.setOnClickListener(v -> dismiss());
        restoreTodo();
    }

    private void restoreTodo() {
        Todo todo = (Todo) getArguments().getSerializable(TODO);
        if (todo != null) {
            isRestore = true;
            restoreViewModel(todo);
            restoreView(todo);
        }
    }

    private void restoreViewModel(Todo todo) {
        mViewModel.restore(todo);
    }

    private void restoreView(Todo todo) {
        mTitleEditText.setText(todo.getTitle());
        mPriorityPicker.setPriority(todo.getPriority());
        mDueDatePicker.setDueDate(todo.getDueDate());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindViewModel();
    }

    private void sendCallbackSuccess(Todo todo) {
        if (isRestore) {
            mCallbackSuccess.onUpdateSuccess(todo);
        } else {
            mCallbackSuccess.onCreateSuccess(todo);
        }
    }

    private void bindViewModel() {
        // Save action
        mSaveButton.setOnClickListener(v -> startObserveInBackground(mViewModel.save()).subscribe(todo -> {
            sendCallbackSuccess(todo);
            dismiss();
        }, throwable -> {
            showShortToast(throwable.getMessage());
        }));

        // Bind due date
        startObserve(mDueDatePicker.date()).subscribe(date -> {
            mViewModel.setDate(date);
        });

        // Bind priority
        startObserve(mPriorityPicker.priority()).subscribe(priority -> {
            mViewModel.setPriority(priority);
        });

        // Bind title
        mTitleEditText.addTextChangedListener(new TextChangeAdapter() {
            @Override
            public void onTextChanged(String title) {
                mViewModel.setTitle(title);
            }
        });

        // Bind toast
        startObserve(mViewModel.toast()).subscribe(this::showShortToast);
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
