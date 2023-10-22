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
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    private List<Meeting> meetingsList;
    private ArrayList<Long> contactIDs;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    private String[] timeSMS_array;
    private String hourSMS;
    private String minuteSMS;



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


        Button toContactsButt =findViewById(R.id.toContactsButton);
        Intent toContacts =new Intent(this, ContactActivity.class);
        toContactsButt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(toContacts);
            } });

        Button editMeetingsButt =findViewById(R.id.editMeetingButton);
        Intent editMeetingIntent =new Intent(this, EditMeetingActivity.class);

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

        boolean prefNotif = sharedPref.getBoolean("activateNotification",true);
        String Str_prefNotif = String.valueOf(prefNotif);
        Log.d("prefNotification",Str_prefNotif);
        List<Long> meetingsIDs = meetingAdapter.getMeetingIdsWithTodayDate(this);
        for(long i: meetingsIDs){
            Log.d("Matching IDs","Meeting with ID +"+String.valueOf(i) + " takesplace today.");
        }


        if(Str_prefNotif  == "true" && meetingsIDs.size() >0 ){
            Intent intent = new Intent(this,MyPeriodicService.class);
            this.startService(intent);
        }

        boolean checkedSMS = sharedPref.getBoolean("activateSMS", true);
        String Str_checkedSMS = String.valueOf(checkedSMS);
        Log.d("checkedSMS", Str_checkedSMS);

        String timeSMS = sharedPref.getString("timeSMS","");
        Log.d("timeSMS", timeSMS);
        boolean containNum = containsNumbers(timeSMS);

        timeSMS_array = timeSMS.split("[.:\\\\/-]");

        for(String i: timeSMS_array) {
            Log.d("timeSMS_array","The array contains: " +i);
        }

        if(!timeSMS.isEmpty() && containNum == true && timeSMS_array.length <= 4){
            int int_hourSMS = Integer.parseInt(timeSMS_array[0]);
            int int_minSMS = Integer.parseInt(timeSMS_array[1]);

            if(int_hourSMS <= 24 && int_minSMS <= 59) {

            if(timeSMS_array[0].length() == 2){
                if(timeSMS_array[0] != "00"){
                    String checkHour1 = String.valueOf(timeSMS_array[0].charAt(0));
                    if(checkHour1 == "0") {
                            hourSMS = String.valueOf(timeSMS_array[0].charAt(1));
                        }
                    else {
                        hourSMS = String.valueOf(timeSMS_array[0]);
                    }
                }
                else {
                    hourSMS = "00";
                }
            }
            else {
                hourSMS = String.valueOf(timeSMS_array[0]);
            }
            minuteSMS = String.valueOf(timeSMS_array[1]);

                }
            }


        Log.d("timeSMS_hour", "Hour set is: " +hourSMS);
        Log.d("timeSMS_minute", "Minute set is: " +minuteSMS);

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                            String[]{android.Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }


    }


    public static boolean containsNumbers(String input) {
        // Define a regular expression pattern to match numbers
        Pattern pattern = Pattern.compile(".*\\d+.*");

        // Use a Matcher to check if the input String contains numbers
        Matcher matcher = pattern.matcher(input);

        // Return true if numbers are found, false otherwise
        return matcher.matches();
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

    private void sendMessage() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String phoneNumber = null;
        contactIDs = meetingAdapter.checkMeetingDate();
        if(!contactIDs.isEmpty()) {
            for (int i = 0; i < contactIDs.size(); i++) {
                phoneNumber = contactAdapter.getContactPhone(contactIDs.get(i));

                String defaultSMS = "Hei. Dette er en påminelse på avtalen som vi har i dag. Vi ses!";
                String message = sharedPreferences.getString("messageSMS",defaultSMS);

                Log.d("phoneSMS", "Phone number is " +phoneNumber);

                if (!phoneNumber.isEmpty() && !message.isEmpty()) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);


                    Toast.makeText(this, "SMS sendt", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Skriv inn telefon og melding.", Toast.LENGTH_SHORT).show();
                }
            }}
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

    /*
        // Insert a new contact
        Contact contact = new Contact("Arald", "123-456-7890");
        long contactId = contactAdapter.insertContact(contact);


        // Insert a meeting associated with the contact
        Meeting meeting = new Meeting("10:00 AM", "2023-10-15", "Meeting Room A","Meeting with Arald", contactId);
        meetingAdapter.insertMeeting(meeting);

        Cursor contactCursor = contactAdapter.getAllContacts();

     */


        /*
        LocalTime currentTime = LocalTime.now();
        String timeNow = currentTime.toString();

        if (Str_sms == "true" && selectedTime == timeNow){
                sendMessage();
        }


        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }

         */
