package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskDataSource dataSource;
    private ArrayAdapter<Task> taskArrayAdapter;

    private List<Task> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new TaskDataSource(this);
        dataSource.open();
        ListView oppgaveListView = findViewById(R.id.listView);
        tasks = dataSource.findAllTasks();
        taskArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tasks);
        oppgaveListView.setAdapter(taskArrayAdapter);

        Button contactsButton =findViewById(R.id.contactsButton);
        Intent i=new Intent(this, ContactsActivity.class);
        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(i);
            } });

    }

    @Override
    protected void onResume() {
        dataSource.open(); super.onResume(); }
    @Override
    protected void onPause() {
        dataSource.close(); super.onPause(); }
}

