package com.example.s336154_mappe2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EditContactActivity extends AppCompatActivity {
    private ContactAdapter contactAdapter;
    private ArrayAdapter<Contact> contactArrayAdapter;
    private List<Contact> contactsList;
    private DatabaseHelper dbHelper;
    private EditText editContactName, editContactPhone;
    public long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);


        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();


        Button saveContactButton = findViewById(R.id.saveContactButton);
        Button deleteContactButton = findViewById(R.id.deletecontact);
        Button addMeetingButton = findViewById(R.id.contactToMeetingButton);

        long contactID = getIntent().getExtras().getLong("contactId", 0);
        int position =  getIntent().getExtras().getInt("position", 0);
        String contactName = getIntent().getExtras().getString("contactName", null);
        String contactPhone = getIntent().getExtras().getString("contactPhone", null);

        editContactName = findViewById(R.id.editContactName);
        editContactPhone = findViewById(R.id.editContactPhone);


        editContactName.setText(contactName);
        editContactPhone.setText(contactPhone);



      //  editContactName.setText();


        contactsList = (List<Contact>) contactAdapter.getContactsList();

        contactArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, contactsList);



        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the user input from editContactName and editContactPhone
                String name = editContactName.getText().toString();
                String phone = editContactPhone.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    // Create a new Contact object and save it to the database

                    Contact contact = new Contact(name, phone);
                    contactAdapter.updateContact(contactID,contact);
                    contactArrayAdapter.add(contact);

                    if(name != contactName && phone != contactPhone) {
                        Toast.makeText(EditContactActivity.this, "Endring ble lagret.",
                                Toast.LENGTH_LONG).show();
                    }

                }
                else {

                    Toast.makeText(EditContactActivity.this, " Du må fylle ut Navn og Telefon nummer.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        Button fromAddToContacts = findViewById(R.id.fromAddToContacts);

        Intent addToContactsIntent = new Intent(this, ContactActivity.class);
        fromAddToContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(addToContactsIntent);
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
                    contactAdapter.updateContact(contactID, contact);
                    contactArrayAdapter.add(contact);

                    contactArrayAdapter.remove(contactArrayAdapter.getItem(position));
                    contactArrayAdapter.notifyDataSetChanged();


                    if(name != contactName && phone != contactPhone) {
                        Toast.makeText(EditContactActivity.this, "Endring ble lagret.",
                                Toast.LENGTH_LONG).show();
                    }

                    editContactName.setText("");
                    editContactPhone.setText("");

                    addContactMeetingIntent.putExtra("contactId", contactID);
                    Log.d("Saved contact ID", String.valueOf(contactID));
                    startActivity(addContactMeetingIntent);
                }

                else {
                    Toast.makeText(EditContactActivity.this, " Du må fylle ut Navn og Telefon nummer.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameDeleted = editContactName.getText().toString();


                AlertDialog.Builder builder = new AlertDialog.Builder(deleteContactButton.getContext());
                builder.setMessage("Vil du slette " +nameDeleted+ " ?")
                        .setPositiveButton("Slett", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform the contact deletion here
                                contactArrayAdapter.remove(contactArrayAdapter.getItem(position));
                                contactArrayAdapter.notifyDataSetChanged();

                                contactAdapter.deleteContact(contactID);
                                Toast.makeText(EditContactActivity.this, nameDeleted + " er slettet fra kontakt liste.",
                                        Toast.LENGTH_LONG).show();
                                startActivity(addToContactsIntent);
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


