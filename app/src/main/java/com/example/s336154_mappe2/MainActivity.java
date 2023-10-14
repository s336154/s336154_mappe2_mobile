package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;

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
    private EditText taskEditName;
    private EditText taskEditNr;
    private List<Task> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new TaskDataSource(this);
        dataSource.open();
        taskEditName = findViewById(R.id.editName);
        taskEditNr = findViewById(R.id.editNr);
        ListView oppgaveListView = findViewById(R.id.listView);
        tasks = dataSource.findAllTasks();
        taskArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tasks);
        oppgaveListView.setAdapter(taskArrayAdapter);


        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskEditName.getText().toString();
                String taskNr = taskEditNr.getText().toString();
                if (!taskNr.isEmpty() && !taskNr.isEmpty()) {
                    Task task = dataSource.addTask(taskName, taskNr);
                    taskArrayAdapter.add(task);
                    taskEditName.setText("");
                    taskEditNr.setText("");
                } }
        });


        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                long taskId=0;
                taskId = Long.parseLong(taskEditName.getText().toString());
                if (taskId > 0) { dataSource.deleteTask(taskId);
                    tasks = dataSource.findAllTasks();
                    taskArrayAdapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1, tasks);
                    oppgaveListView.setAdapter(taskArrayAdapter);
                    taskEditName.setText("");
                    taskEditNr.setText("");
                } } });
    }

    @Override
    protected void onResume() {
        dataSource.open(); super.onResume(); }
    @Override
    protected void onPause() {
        dataSource.close(); super.onPause(); }
}

