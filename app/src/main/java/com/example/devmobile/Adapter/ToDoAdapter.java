package com.example.devmobile.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.devmobile.AddNewTodo;
import com.example.devmobile.MainActivity;
import com.example.devmobile.Model.ToDoModel;
import com.example.devmobile.R;
import com.example.devmobile.Utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> todoList;
    private MainActivity activity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox todo;

        ViewHolder(View view) {
            super(view);
            todo = view.findViewById(R.id.todoCheckBox);
        }
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        ToDoModel item = todoList.get(position);
        holder.todo.setText(item.getTodo());
        holder.todo.setChecked(toBoolean(item.getStatus()));
        holder.todo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public int getItemCount() {
        return todoList.size();
    }

    private boolean toBoolean(int number) {
        return number != 0;
    }

    public void setTodos(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void editItem(int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("todo", item.getTodo());
        AddNewTodo fragment = new AddNewTodo();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTodo.TAG);
    }

    public void deleteItem(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTodo(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return activity;
    }
}
