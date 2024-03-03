package com.example.devmobile;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.devmobile.Model.ToDoModel;
import com.example.devmobile.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTodo extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newTodoText;
    private Button newTodoSaveButton;
    private DatabaseHandler db;

    public static AddNewTodo newInstance() {
        return new AddNewTodo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_todo, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTodoText = getView().findViewById(R.id.addTodoText);
        newTodoSaveButton = getView().findViewById(R.id.addTodoButton);
        db = new DatabaseHandler(getActivity());
        db.openDatabase();
        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String todo = bundle.getString("todo");
            newTodoText.setText(todo);
            if (todo.length() > 0) {
                newTodoSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
        }
        newTodoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    newTodoSaveButton.setEnabled(false);
                    newTodoSaveButton.setTextColor(Color.GRAY);
                } else {
                    newTodoSaveButton.setEnabled(true);
                    newTodoSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        newTodoSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTodoText.getText().toString();
                if (finalIsUpdate) {
                    db.updateTodo(bundle.getInt("id"), text);
                } else {
                    ToDoModel todo = new ToDoModel();
                    todo.setTodo(text);
                    todo.setStatus(0);
                    db.insertTodo(todo);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
