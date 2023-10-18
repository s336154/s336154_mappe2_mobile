package com.example.s336154_mappe2;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    private List<Meeting> meetingsList;
    private ArrayAdapter<Meeting> meetingArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingAdapter = new MeetingAdapter(this);
        meetingAdapter.open();

        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();

        ListView meetingListView = (ListView) findViewById(R.id.listView);
        meetingsList = (List<Meeting>) meetingAdapter.getMeetingsList();


        meetingArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, meetingsList);
        meetingListView.setAdapter(meetingArrayAdapter);
        meetingArrayAdapter.notifyDataSetChanged();


/*
        // Insert a new contact
        Contact contact = new Contact("Contact nr. 2", "123-456-7890");
        long contactId = contactAdapter.insertContact(contact);


        // Insert a meeting associated with the contact
        Meeting meeting = new Meeting("10:00 AM", "2023-10-15", "Meeting Room A", contactId);
        meetingAdapter.insertMeeting(meeting);
 */

        // Retrieve all contacts and meetings
        Cursor contactCursor = contactAdapter.getAllContacts();
        Cursor meetingCursor = meetingAdapter.getAllMeetings();

        meetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Meeting selectedItem = (Meeting) parent.getItemAtPosition(position);
                // Handle item click here
                Toast.makeText(MainActivity.this, "Trykket på et avtale med dato : "
                        + selectedItem.getDate() + " på " + selectedItem.getPlace() +
                        " kl " + selectedItem.getTime(), Toast.LENGTH_LONG).show();

                Log.d("Selected contact ID", "Name selected is: " + String.valueOf(selectedItem.getPlace()) +
                        "  ID is: " + String.valueOf(selectedItem.getId()));
            };
                                               });

        // Handle the retrieved data as needed

        Button toContactsButt =findViewById(R.id.toContactsButton);
        Intent toContacts =new Intent(this, ContactActivity.class);
        toContactsButt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(toContacts);
            } });

        Button toMeetingsButt =findViewById(R.id.editMeetingButton);
        Intent toMeetings =new Intent(this, MeetingActivity.class);
        toMeetingsButt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(toMeetings);
            } });

        /*

        meetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Meeting selectedItem = (Meeting) parent.getItemAtPosition(position);
                // Handle item click here
                Toast.makeText(MainActivity.this, "Trykket på et avtale med dato : "
                        +selectedItem.getDate()+" på " + selectedItem.getPlace()+
                        " kl " +  selectedItem.getTime(), Toast.LENGTH_LONG).show();

                Log.d("Selected contact ID", "Name selected is: " + String.valueOf(selectedItem.getPlace()) +
                        "  ID is: " + String.valueOf(selectedItem.getId()));

                String nameDeleted = selectedItem.getName();

                deleteContactButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(deleteContactButton.getContext());
                        builder.setMessage("Slett kontakt ")
                                .setPositiveButton("Slett", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Perform the contact deletion here
                                        contactArrayAdapter.remove(contactArrayAdapter.getItem(position));
                                        contactArrayAdapter.notifyDataSetChanged();

                                        Boolean deleted = contactAdapter.deleteContact(selectedItem.getId());
                                        Toast.makeText(ContactActivity.this, "Contact " + nameDeleted + " is deleted.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User clicked "Cancel," do nothing
                                        return;
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                addContactMeeting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(addContactMeetingIntent);
                    }
                });
            }
        });

         */
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
