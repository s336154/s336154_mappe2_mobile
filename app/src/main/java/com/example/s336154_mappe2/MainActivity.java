package com.example.s336154_mappe2;

import static android.app.ProgressDialog.show;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
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

    private boolean sms;

    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

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
        Contact contact = new Contact("Arald", "123-456-7890");
        long contactId = contactAdapter.insertContact(contact);


        // Insert a meeting associated with the contact
        Meeting meeting = new Meeting("10:00 AM", "2023-10-15", "Meeting Room A","Meeting with Arald", contactId);
        meetingAdapter.insertMeeting(meeting);

        Cursor contactCursor = contactAdapter.getAllContacts();

     */




        // Retrieve all contacts and meetings

        Cursor meetingCursor = meetingAdapter.getAllMeetings();

        Button editMeeting =findViewById(R.id.editMeetingButton);
        Intent editMeetingIntent =new Intent(this, EditMeetingActivity.class);

        meetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Meeting selectedItem = (Meeting) parent.getItemAtPosition(position);
                // Handle item click here
                Toast.makeText(MainActivity.this, "Trykket på et avtale med dato : "
                        + selectedItem.getDate() + " på " + selectedItem.getPlace() +
                        " kl " + selectedItem.getTime(), Toast.LENGTH_LONG).show();

                Log.d("Selected contact ID", "Name of place selected is: " + String.valueOf(selectedItem.getPlace()) +
                        "  ID is: " + String.valueOf(selectedItem.getId()));


                editMeeting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d("contactID","contactID from Main is: "
                                +String.valueOf(selectedItem.getContactId()));

                        Log.d("contactID","meetingID from Main is: "
                                +String.valueOf(selectedItem.getId()));

                        editMeetingIntent.putExtra("contactID", selectedItem.getContactId());
                        editMeetingIntent.putExtra("meetingID", selectedItem.getId());
                        editMeetingIntent.putExtra("meetingComment", selectedItem.getComment());
                        editMeetingIntent.putExtra("meetingPlace",selectedItem.getPlace());
                        editMeetingIntent.putExtra("meetingDate",selectedItem.getDate() );
                        editMeetingIntent.putExtra("meetingTime",selectedItem.getTime() );
                        editMeetingIntent.putExtra("position",position );
                        startActivity(editMeetingIntent);
                    }
                });
            };
                                               });

        // Handle the retrieved data as needed

        Button toContactsButt =findViewById(R.id.toContactsButton);
        Intent toContacts =new Intent(this, ContactActivity.class);
        toContactsButt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(toContacts);
            } });

        Button editMeetingsButt =findViewById(R.id.editMeetingButton);
        Intent editMeetingsIntent =new Intent(this, EditMeetingActivity.class);



        Button editMeetingButt =findViewById(R.id.editMeetingButton);
        meetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Meeting selectedItem = (Meeting) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Trykket på et avtale med dato : "
                        + selectedItem.getDate() + " på " + selectedItem.getPlace() +
                        " kl " + selectedItem.getTime(), Toast.LENGTH_LONG).show();

                Log.d("Selected contact ID", "Name selected is: " + String.valueOf(selectedItem.getPlace()) +
                        "  ID is: " + String.valueOf(selectedItem.getId()));


                String dateDeleted = selectedItem.getDate();
                long meetingId = selectedItem.getId();

                Log.d("meeting Id", "ID is: " + String.valueOf(meetingId));



                editMeetingsButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        editMeetingIntent.putExtra("contactID", selectedItem.getContactId());
                        editMeetingIntent.putExtra("meetingID", selectedItem.getId());
                        editMeetingIntent.putExtra("meetingTime", selectedItem.getTime());
                        editMeetingIntent.putExtra("meetingDate", selectedItem.getDate());
                        editMeetingIntent.putExtra("meetingPlace", selectedItem.getPlace());
                        editMeetingIntent.putExtra("meetingComment", selectedItem.getComment());
                        editMeetingIntent.putExtra("position",position );
                        startActivity(editMeetingIntent);

                    }
                });




            }
        });

        Button preferanseButt = findViewById(R.id.preferenseButton);
        Intent prefIntent =new Intent(this,SettingsActivity.class);
        preferanseButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(prefIntent);
            }
        });

        SharedPreferences sharedPref = getDefaultSharedPreferences(this);
        sms = sharedPref.getBoolean("activateSMS", true);
        String Str_sms = String.valueOf(sms);

        Log.d("Logged", String.valueOf(sms));

        if (Str_sms == "true"){

    //        sendMessage();

        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedTime = sharedPreferences.getString("user_selected_time", "12:00"); // Default time if not set



        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
            } else {

                Toast.makeText(this, "SMS Tillatelse ikke gitt. Du kan ikke sende SMS.", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPref = getDefaultSharedPreferences(this);
                sharedPref.edit().putBoolean("activateSMS", false).apply();

            }
        }


    }

    /*
    private void sendMessage() {
        String phoneNumber = telefon.getText().toString();
        String message = melding.getText().toString();

        if (!phoneNumber.isEmpty() && !message.isEmpty()) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sendt", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Skriv inn telefon og melding.", Toast.LENGTH_SHORT).show(); } }

     */

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
