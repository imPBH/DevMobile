package com.example.devmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Todo> {
    public CustomAdapter(@NonNull Context context, ArrayList<Todo> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Todo todo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_todo, parent, false);
        }
        TextView todoTitle = convertView.findViewById(R.id.todoTitle);
        CheckBox done = convertView.findViewById(R.id.doneStatus);

        todoTitle.setText(todo.getTitle());
        done.setChecked(todo.getDone());
        done.setOnClickListener(view -> {
            todo.setDone(done.isChecked());
        });
        return convertView;
    }
}
