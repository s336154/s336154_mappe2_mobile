package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DataSource(this);
        dataSource.open();

        // Insert a new contact
        Contact contact = new Contact("John Doe", "123-456-7890");
        long contactId = dataSource.insertContact(contact);

        // Insert a meeting associated with the contact
        Meeting meeting = new Meeting("10:00 AM", "2023-10-15", "Meeting Room A", contactId);
        dataSource.insertMeeting(meeting);

        // Retrieve all contacts and meetings
        Cursor contactCursor = dataSource.getAllContacts();
        Cursor meetingCursor = dataSource.getAllMeetings();

        // Handle the retrieved data as needed

        Button toContactsButt =findViewById(R.id.toContactsButton);
        Intent toContacts =new Intent(this, ContactActivity.class);
        toContactsButt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(toContacts);
            } });

        Button toMeetingsButt =findViewById(R.id.toMeetingsButton);
        Intent toMeetings =new Intent(this, MeetingActivity.class);
        toMeetingsButt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(toMeetings);
            } });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
