package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class EditMeetingActivity extends AppCompatActivity {
    private MeetingAdapter meetingAdapter;
    private EditText editMeetingTime, editMeetingDate, editMeetingPlace, editMeetingComment;

    private ArrayAdapter<Meeting> meetingArrayAdapter;
    private List<Meeting> meetingsList;
    public long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meeting);


        meetingAdapter = new MeetingAdapter(this);
        meetingAdapter.open();

        editMeetingTime = findViewById(R.id.editTime);
        editMeetingDate = findViewById(R.id.editDate);
        editMeetingPlace = findViewById(R.id.editPlace);
        editMeetingComment = findViewById(R.id.editComment);


        // Implement onClick listeners for save and delete buttons
        Button saveMeetingButton = findViewById(R.id.saveMeetingButt);
        Button deleteMeetingButton = findViewById(R.id.deleteMeetingButt);
        Button meetingToMainButton = findViewById(R.id.meetingToMain);







        meetingsList = (List<Meeting>) meetingAdapter.getMeetingsList();
        meetingArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, meetingsList);

        long contactID = getIntent().getExtras().getLong("contactID", 0);
        long meetingID = getIntent().getExtras().getLong("meetingID", 0);
        int position =  getIntent().getExtras().getInt("position", 0);
        String meetingTime = getIntent().getExtras().getString("meetingTime", null);
        String meetingDate = getIntent().getExtras().getString("meetingDate", null);
        String meetingPlace = getIntent().getExtras().getString("meetingPlace", null);
        String meetingComment = getIntent().getExtras().getString("meetingComment", null);

        Log.d("contactID","contactID is " +String.valueOf(contactID));


        editMeetingComment.setText(meetingComment);
        editMeetingDate.setText(meetingDate);
        editMeetingPlace.setText(meetingPlace);
        editMeetingTime.setText(meetingTime);



        saveMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Read the user input from editContactName and editContactPhone
                String time = editMeetingTime.getText().toString();
                String date = editMeetingDate.getText().toString();
                String place = editMeetingPlace.getText().toString();
                String comment = editMeetingComment.getText().toString();

                if (!time.isEmpty() && !date.isEmpty() && !place.isEmpty()) {
                    // Create a new Contact object and save it to the database


                    Meeting meeting = new Meeting(time, date, place,comment, contactID);
                    meetingAdapter.updateMeeting(meetingID,meeting);
                    meetingArrayAdapter.add(meeting);

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



        Intent toMainIntent = new Intent(this, MainActivity.class);
        meetingToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(toMainIntent);
            }
        });



        deleteMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /*
                String dateDeleted = editMeetingDate.getText().toString();
                String timeDeleted = editMeetingTime.getText().toString();
                String placeDeleted = editMeetingPlace.getText().toString();
                String commentDeleted = editMeetingComment.getText().toString();

             */


                AlertDialog.Builder builder = new AlertDialog.Builder(deleteMeetingButton.getContext());
                builder.setMessage("Vil du slette avtalen ?")
                        .setPositiveButton("Slett", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform the contact deletion here
                                meetingArrayAdapter.remove(meetingArrayAdapter.getItem(position));
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
