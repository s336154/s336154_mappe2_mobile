package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.DatePicker;
import android.widget.TimePicker;



public class MeetingActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private MeetingAdapter meetingAdapter;
    private EditText  editMeetingPlace, editMeetingComment;
    private MeetingCustomBaseAdapter meetingArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_layout);

        meetingAdapter = new MeetingAdapter(this);
        meetingAdapter.open();


        editMeetingPlace = findViewById(R.id.editMeetingPlace);
        editMeetingComment = findViewById(R.id.editMeetingComment);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        timePicker.setIs24HourView(true);

        Button saveMeetingButton = findViewById(R.id.saveMeetingButton);



        long contactID = getIntent().getExtras().getLong("contactId", 0);
        Log.d("contactID","contactID is " +String.valueOf(contactID));


        MeetingCustomBaseAdapter meetingArrayAdapter = new MeetingCustomBaseAdapter(getApplicationContext(),
                meetingAdapter.getMeetingsList());


        Button toContactsButt = findViewById(R.id.toContactsButton);
        Intent toContactsIntent =new Intent(this,ContactActivity.class);
        toContactsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toContactsIntent);
            }
        });


        Button toMainButt = findViewById(R.id.toMainButton);
        Intent toMainIntent =new Intent(this,MainActivity.class);
        toMainButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toMainIntent);
            }
        });





        saveMeetingButton.setOnClickListener(new View.OnClickListener() {

            private String  pad(int value) {
                // Helper method to ensure leading zero for single-digit values
                return (value < 10) ? "0" + value : String.valueOf(value);
            }

            @Override
            public void onClick(View v) {

                String place = editMeetingPlace.getText().toString();
                String comment = editMeetingComment.getText().toString();


                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1; // Month is zero-based, so add 1
                int day = datePicker.getDayOfMonth();
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();


                String date = year + "-" + pad(month) + "-" + pad(day);
                String time = pad(hour) + ":" + pad(minute);

                String dateTimeString = date + " " + time;



                if (!time.isEmpty() && !place.isEmpty() && !date.isEmpty()) {



                    Meeting meeting = new Meeting(time, date, place,comment, contactID);

                    meetingAdapter.insertMeeting(meeting);
                    MeetingCustomBaseAdapter meetingArrayAdapter = new MeetingCustomBaseAdapter(getApplicationContext(),
                            meetingAdapter.getMeetingsList());

                    // Handle item click here
                    Toast.makeText(MeetingActivity.this,  "Avtalen er lagret.",
                            Toast.LENGTH_LONG).show();

                    editMeetingPlace.setText("");
                    editMeetingComment.setText("");
                }


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
