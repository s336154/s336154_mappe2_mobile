package com.example.s336154_mappe2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
public class MeetingActivity extends AppCompatActivity {
    private MeetingAdapter meetingAdapter;
    private EditText editMeetingTime, editMeetingDate, editMeetingPlace, editMeetingComment;

    private ArrayAdapter<Meeting> meetingArrayAdapter;
    private List<Meeting> meetingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_layout);

        meetingAdapter = new MeetingAdapter(this);
        meetingAdapter.open();

        editMeetingTime = findViewById(R.id.editMeetingTime);
        editMeetingDate = findViewById(R.id.editMeetingDate);
        editMeetingPlace = findViewById(R.id.editMeetingPlace);
        editMeetingComment = findViewById(R.id.editMeetingComment);


        // Implement onClick listeners for save and delete buttons
        Button saveMeetingButton = findViewById(R.id.saveMeetingButton);
        Button meetingToContactsButton = findViewById(R.id.meetingToContacts);


       long contactID = getIntent().getExtras().getLong("contactId", 0);
       Log.d("contactID","contactID is " +String.valueOf(contactID));


        meetingsList = (List<Meeting>) meetingAdapter.getMeetingsList();
        meetingArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, meetingsList);



        saveMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the user input from editContactName and editContactPhone
                String time = editMeetingTime.getText().toString();
                String date = editMeetingDate.getText().toString();
                String place = editMeetingPlace.getText().toString();
                String comment = editMeetingComment.getText().toString();

                if (!time.isEmpty() && !place.isEmpty() && !date.isEmpty()) {
                    // Create a new Contact object and save it to the database

                //    Meeting meeting = new Meeting(time, date, place,comment, contactID);
                    Meeting meeting = new Meeting(time, date, place,comment, contactID);
               //     meetingAdapter.insertMeeting(meeting);

                    long contactId = meetingAdapter.insertMeeting(meeting);
                    meetingArrayAdapter.add(meeting);

                    // Handle item click here
                    Toast.makeText(MeetingActivity.this,  "Avtalen er lagret.",
                            Toast.LENGTH_LONG).show();

                    editMeetingTime.setText("");
                    editMeetingDate.setText("");
                    editMeetingPlace.setText("");
                    editMeetingComment.setText("");
                }


            }
        });

        Intent toContactsIntent = new Intent(this, ContactActivity.class);
        meetingToContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toContactsIntent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        meetingAdapter.open();}
    @Override
    protected void onPause() {
        super.onPause();
        meetingAdapter.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        meetingAdapter.close();
    }
}
