package com.example.s336154_mappe2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
public class MeetingActivity extends AppCompatActivity {
    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    private EditText editMeetingTime, editMeetingDate, editMeetingPlace;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_layout);

        meetingAdapter = new MeetingAdapter(this);
        meetingAdapter.open();

        editMeetingTime = findViewById(R.id.editMeetingTime);
        editMeetingDate = findViewById(R.id.editMeetingDate);
        editMeetingPlace = findViewById(R.id.editMeetingPlace);

        // Implement onClick listeners for save and delete buttons
        Button saveMeetingButton = findViewById(R.id.saveMeetingButton);
        Button deleteContactButton = findViewById(R.id.deleteMeetingButton);

        saveMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the user input from editContactName and editContactPhone
                String time = editMeetingTime.getText().toString();
                String date = editMeetingDate.getText().toString();
                String place = editMeetingPlace.getText().toString();

            }
        });

        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        contactAdapter.open();
        meetingAdapter.open();}
    @Override
    protected void onPause() {
        super.onPause();
        contactAdapter.close();
        meetingAdapter.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactAdapter.close();
        meetingAdapter.close();
    }
}
