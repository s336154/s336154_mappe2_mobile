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



        DatabaseHelper dbHelper = new DatabaseHelper(this); // Replace 'context' with your actual context
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String columnName = "CONTACTS_COLUMN_ID"; // Replace with the actual column name
        String columnValue = "John"; // Replace with the actual value you're searching for

        String query = "SELECT id FROM contacts WHERE " + columnName + " = ?";
        String[] selectionArgs = { columnValue };


        Cursor cursor = db.rawQuery(query, selectionArgs);





        saveMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the user input from editContactName and editContactPhone
                String time = editMeetingTime.getText().toString();
                String date = editMeetingDate.getText().toString();
                String place = editMeetingPlace.getText().toString();

                long contactId = -1; // Initialize with a default value

                if (cursor.moveToFirst()) {
                    contactId = cursor.getLong(0); // Assuming the ID is in the first column (index 0)
                }

                cursor.close();


                // Create a new Contact object and save it to the database
                Meeting meeting = new Meeting(time, date, place, contactId);
                long meetingId = meetingAdapter.insertMeeting(meeting);
            }
        });

        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement code to delete a contact
                // You can get the contact's ID and call contactAdapter.deleteContact(contactId)
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        meetingAdapter.close();
    }
}
