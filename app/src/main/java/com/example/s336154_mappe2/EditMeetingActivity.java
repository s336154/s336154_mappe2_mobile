package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class EditMeetingActivity extends AppCompatActivity {
    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    private EditText editMeetingPlace, editMeetingComment;

    private DatePicker datePicker;
    private TimePicker timePicker;
    private String[] dateArray, timeArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meeting);


        meetingAdapter = new MeetingAdapter(this);
        meetingAdapter.open();

        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();

        editMeetingPlace = findViewById(R.id.editPlace);
        editMeetingComment = findViewById(R.id.editComment);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        timePicker.setIs24HourView(true);

        Button saveMeetingButton = findViewById(R.id.saveMeetingButt);
        Button deleteMeetingButton = findViewById(R.id.deleteMeetingButt);
        Button meetingToContactButton = findViewById(R.id.meetingToContact);

        MeetingCustomBaseAdapter meetingArrayAdapter = new MeetingCustomBaseAdapter(getApplicationContext(),
                meetingAdapter.getMeetingsList());


       // long contactID = getIntent().getExtras().getLong("contactID", 0);
        long meetingID = getIntent().getExtras().getLong("meetingID", 0);
        int position =  getIntent().getExtras().getInt("position", 0);
        String meetingTime = getIntent().getExtras().getString("meetingTime", null);
        String meetingDate = getIntent().getExtras().getString("meetingDate", null);
        String meetingPlace = getIntent().getExtras().getString("meetingPlace", null);
        String meetingComment = getIntent().getExtras().getString("meetingComment", null);

        long contactID = meetingAdapter.getContactIdForMeeting(meetingID);

        Log.d("contactID","contactID in EditMeeting is " +String.valueOf(contactID));
        Log.d("contactID","meetingID in EditMeeting is " +String.valueOf(meetingID));

        editMeetingComment.setText(meetingComment);
        editMeetingPlace.setText(meetingPlace);


        dateArray = meetingDate.split("-");
        timeArray = meetingTime.split(":");

        String checkHour = String.valueOf(timeArray[0].charAt(0));
        String checkMinute = String.valueOf(timeArray[1].charAt(0));
        String checkDay = String.valueOf(dateArray[2].charAt(0));

        if(dateArray[1].length()==2) {
            String checkMonth = String.valueOf(dateArray[1].charAt(0));

            if (checkMonth == "0") {
                dateArray[1].split("0", 1);
            }
        }


        if (checkHour == "0"){
            timeArray[0].split("0",1);
        }

        if (checkMinute == "0"){
            timeArray[1].split("0",1);
        }


        if (checkDay == "0"){
            dateArray[2].split("0",1);
        }

        int timeHour = Integer.parseInt(timeArray[0]);
        int timeMinute = Integer.parseInt(timeArray[1]);

        int dateYear = Integer.parseInt(dateArray[0]);
        int dateMonth = Integer.parseInt(dateArray[1]) -1;
        int dateDay = Integer.parseInt(dateArray[2]);

        Log.d("Rediger avtale","Month is: " +String.valueOf(dateArray[1]));
        Log.d("Rediger avtale","Day is: " +String.valueOf(dateArray[2]));



        datePicker.init(dateYear, dateMonth, dateDay, null);
        timePicker.setHour(timeHour);
        timePicker.setMinute(timeMinute);


        saveMeetingButton.setOnClickListener(new View.OnClickListener() {

            private String  pad(int value) {

                return (value < 10) ? "0" + value : String.valueOf(value);
            }
            @Override
            public void onClick(View v) {

                String place = editMeetingPlace.getText().toString();
                String comment = editMeetingComment.getText().toString();


                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                String date = year + "-" + pad(month) + "-" + pad(day);
                String time = pad(hour) + ":" + pad(minute);

                String dateTimeString = date + " " + time;


                if (!time.isEmpty() && !date.isEmpty() && !place.isEmpty()) {

                    Meeting meeting = new Meeting(time, date, place,comment, contactID);

                    meetingAdapter.updateMeeting(meetingID,meeting);

                    MeetingCustomBaseAdapter meetingArrayAdapter = new MeetingCustomBaseAdapter(getApplicationContext(),
                            meetingAdapter.getMeetingsList());

                    if(time != meetingTime && place != meetingPlace && date != meetingDate &&
                            comment != meetingComment) {

                        Toast.makeText(EditMeetingActivity.this, "Endring ble lagret.",
                                Toast.LENGTH_LONG).show();
                    }

                }
                else {

                    Toast.makeText(EditMeetingActivity.this, " Du må fylle ut Tid, Dato" +
                                    " og Sted.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });



        Intent toContactIntent = new Intent(this, EditContactActivity.class);
        Intent toMainIntent = new Intent(this, MainActivity.class);
        meetingToContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("EditContactID", "Contact ID is: " +String.valueOf(contactID));

                boolean finnes = contactAdapter.idExist(contactID);
                Log.d("EditContactID", "Contact availbility is: " +String.valueOf(finnes));
                if(finnes) {

                    toContactIntent.putExtra("contactId", contactID);

                    startActivity(toContactIntent);
                }
                else {
                    Toast.makeText(EditMeetingActivity.this, "Kontakten er ikke i konatktliste.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });



        deleteMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(deleteMeetingButton.getContext());
                builder.setMessage("Vil du slette avtalen ?")
                        .setPositiveButton("Slett", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                meetingArrayAdapter.notifyDataSetChanged();

                                meetingAdapter.deleteMeeting(meetingID);
                                Toast.makeText(EditMeetingActivity.this, "Avtalen er slettet.",
                                        Toast.LENGTH_LONG).show();
                                startActivity(toMainIntent);
                            }
                        })
                        .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                return;
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
}