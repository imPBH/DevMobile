package com.example.devmobile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.devmobile.Adapter.ToDoAdapter;
import com.example.devmobile.Model.ToDoModel;
import com.example.devmobile.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView todosRecyclerView;
    private ToDoAdapter toDoAdapter;
    private List<ToDoModel> todoList;
    private DatabaseHandler db;
    private FloatingActionButton addTodoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        db.openDatabase();
        todoList = new ArrayList<>();
        todosRecyclerView = findViewById(R.id.todosRecyclerView);
        todosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoAdapter = new ToDoAdapter(db, this);
        todosRecyclerView.setAdapter(toDoAdapter);

        addTodoButton = findViewById(R.id.addTodoButton);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTodo.newInstance().show(getSupportFragmentManager(), AddNewTodo.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(toDoAdapter));
        itemTouchHelper.attachToRecyclerView(todosRecyclerView);

        todoList = db.getAllTodos();
        Collections.reverse(todoList);
        toDoAdapter.setTodos(todoList);

        OnBackPressedCallback callback =  new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to quit ?").setTitle("Alert !").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        todoList = db.getAllTodos();
        Collections.reverse(todoList);
        toDoAdapter.setTodos(todoList);
        toDoAdapter.notifyDataSetChanged();
    }
}