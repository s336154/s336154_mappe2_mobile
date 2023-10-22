package com.example.s336154_mappe2;

import static com.example.s336154_mappe2.DatabaseHelper.CONTACTS_COLUMN_NAME;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddContactActivity extends AppCompatActivity {


    private ContactAdapter contactAdapter;
    private EditText editContactName, editContactPhone;
    public long contactId;
    CustomBaseAdapter contactArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);


        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();


        Button saveContactButton = findViewById(R.id.saveContactButton);
        Button addMeetingButton = findViewById(R.id.contactToMeetingButton);

        editContactName = findViewById(R.id.editContactName);
        editContactPhone = findViewById(R.id.editContactPhone);

        contactArrayAdapter = new CustomBaseAdapter(this, contactAdapter.getContactsList());

        Intent toContactsIntent =new Intent(this,ContactActivity.class);

        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the user input from editContactName and editContactPhone
                String name = editContactName.getText().toString();
                String phone = editContactPhone.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    // Create a new Contact object and save it to the database

                    Contact contact = new Contact(name, phone);
                    contactAdapter.insertContact(contact);

                    contactArrayAdapter = new CustomBaseAdapter(getApplicationContext(),
                            contactAdapter.getContactsList());

                    // Handle item click here
                    Toast.makeText(AddContactActivity.this, name + " er lagret i kontakt liste",
                            Toast.LENGTH_LONG).show();

                    editContactName.setText("");
                    editContactPhone.setText("");
                    startActivity(toContactsIntent);
                }
                else {
                    Toast.makeText(AddContactActivity.this, " Du må fylle ut Navn og Telefon nummer.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        Intent addContactMeetingIntent = new Intent(this, MeetingActivity.class);
        addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editContactName.getText().toString();
                String phone = editContactPhone.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    // Create a new Contact object and save it to the database
                    Contact contact = new Contact(name, phone);
                    contactId = contactAdapter.insertContact(contact);

                    contactArrayAdapter = new CustomBaseAdapter(getApplicationContext(),
                            contactAdapter.getContactsList());

                    // Handle item click here
                    Toast.makeText(AddContactActivity.this, name + " er lagret i kontakt liste",
                            Toast.LENGTH_LONG).show();


                    addContactMeetingIntent.putExtra("contactId", contactId);
                    Log.d("Saved contact ID", String.valueOf(contactId));
                    startActivity(addContactMeetingIntent);
                }
                else {
                    Toast.makeText(AddContactActivity.this, " Du må fylle ut Navn og Telefon nummer.",
                            Toast.LENGTH_LONG).show();

                    return;
                }

            }
        });




    }

}


