package com.example.devmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView)findViewById(R.id.listview1);
        final ArrayList<Todo> todos = new ArrayList<Todo>();
        CustomAdapter listItemsAdapter = new CustomAdapter(this, todos);
        for (int i = 0; i < 40; i++) {
            todos.add(new Todo("coucou" + i, false));
        }
        list.setAdapter(listItemsAdapter);
    }
}