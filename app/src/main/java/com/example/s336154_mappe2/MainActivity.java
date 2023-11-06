package com.example.s336154_mappe2;


import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    private List<Meeting> meetingsList;
    private ArrayList<Long> contactIDs;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    List<Long> meetingsIDs;






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

        MeetingCustomBaseAdapter meetingArrayAdapter = new MeetingCustomBaseAdapter(this, meetingsList);

        //    meetingArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, meetingsList);
        meetingListView.setAdapter(meetingArrayAdapter);
        meetingArrayAdapter.notifyDataSetChanged();


        BroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.service.MITTSIGNAL");
        filter.addAction("com.example.service.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver, filter);




        Button toContactsButt = findViewById(R.id.toContactsButton);
        Intent toContacts = new Intent(this, ContactActivity.class);
        toContactsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toContacts);
            }
        });

        Button editMeetingsButt = findViewById(R.id.editMeetingButton);
        Intent editMeetingIntent = new Intent(this, EditMeetingActivity.class);

        meetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Meeting selectedItem = (Meeting) meetingsList.get(position);

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
                        editMeetingIntent.putExtra("position", position);
                        startActivity(editMeetingIntent);

                    }
                });
            }
        });


        Button preferanseButt = findViewById(R.id.preferenseButton);
        Intent prefIntent = new Intent(this, SettingsActivity.class);
        Intent IntentPer = new Intent(this, MyPeriodicService.class);



        preferanseButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getApplicationContext().startService(IntentPer);
                startActivity(prefIntent);
            }
        });

        SharedPreferences sharedPref = getDefaultSharedPreferences(this);

        boolean prefNotif = sharedPref.getBoolean("activateNotification", true);
        String Str_prefNotif = String.valueOf(prefNotif);
        Log.d("SMSNotification", Str_prefNotif);

        boolean checkedSMS = sharedPref.getBoolean("activateSMS", true);
        String Str_checkedSMS = String.valueOf(checkedSMS);
        Log.d("checkedSMS", Str_checkedSMS);

        meetingsIDs = meetingAdapter.getMeetingIdsWithTodayDate(this);


        if (Str_prefNotif == "true" && meetingsIDs.size() > 0) {
            Intent intent = new Intent(this, MyPeriodicService.class);
            this.startService(intent);
        }



        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                            String[]{android.Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        meetingsIDs = meetingAdapter.getMeetingIdsWithTodayDate(this);
        for (long i : meetingsIDs) {
            Log.d("Matching IDs", "Meeting with ID: " + String.valueOf(i) + " takesplace today.");
        }



    }


    boolean grantedSMS;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        SharedPreferences sharedPref = getDefaultSharedPreferences(this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {

                grantedSMS = true;
                sharedPref.edit().putBoolean("activateSMS", true).apply();

            } else {

                Toast.makeText(this, "SMS Tillatelse ikke gitt. Du kan ikke sende SMS.",
                        Toast.LENGTH_SHORT).show();
                sharedPref.edit().putBoolean("activateSMS", false).apply();

            }
        }

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

