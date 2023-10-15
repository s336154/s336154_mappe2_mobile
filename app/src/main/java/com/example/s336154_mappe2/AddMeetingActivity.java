package com.example.s336154_mappe2;

/*
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.List;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity {
    private DataSource dataSource;
    private ArrayAdapter<Task> taskArrayAdapter;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private EditText taskEditPlace;
    private List<Task> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_layout);
        dataSource = new DataSource(this);
        dataSource.open();

        taskEditPlace = findViewById(R.id.editPlace);
        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);

        Button addMeetingButt = findViewById(R.id.addMeetingButt);
        addMeetingButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskPlace = taskEditPlace.getText().toString();

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String taskTime = String.format("%02d:%02d", hour, minute);

                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                String taskDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);

                if (!taskPlace.isEmpty() && !taskTime.isEmpty() && !taskDate.isEmpty()) {
                    Task task = dataSource.addMeeting(taskPlace, taskTime, taskDate);
                    taskArrayAdapter.add(task);
                    taskEditPlace.setText("");

                } }
        });


        Button meetingsContactsButt =findViewById(R.id.meetingsContactsButt);
        Intent toContacts =new Intent(this, ContactActivity.class);
        meetingsContactsButt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(toContacts);
            } });



        Button meetingsTodoListButt =findViewById(R.id.meetingsTodoListButt);
        Intent toTodoList=new Intent(this, MainActivity.class);
        meetingsTodoListButt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(toTodoList);
            } });



    }

    @Override
    protected void onResume() {
        dataSource.open(); super.onResume(); }
    @Override
    protected void onPause() {
        dataSource.close(); super.onPause(); }
}



 */