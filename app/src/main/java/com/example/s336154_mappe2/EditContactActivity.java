package com.example.s336154_mappe2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class EditContactActivity extends AppCompatActivity {
    private ContactAdapter contactAdapter;
    private EditText editContactName, editContactPhone;


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

        if (contactName == null){
            contactName = contactAdapter.getContactName(contactID);
        }

        String contactPhone = getIntent().getExtras().getString("contactPhone", null);

        if (contactPhone == null){
            contactPhone = contactAdapter.getContactPhone(contactID);
        }

        editContactName = findViewById(R.id.editContactName);
        editContactPhone = findViewById(R.id.editContactPhone);


        editContactName.setText(contactName);
        editContactPhone.setText(contactPhone);

        CustomBaseAdapter contactArrayAdapter = new CustomBaseAdapter(getApplicationContext(),
                contactAdapter.getContactsList());

        String finalContactName = contactName;
        String finalContactPhone = contactPhone;

        Intent toContactsIntent =new Intent(this,ContactActivity.class);

        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editContactName.getText().toString();
                String phone = editContactPhone.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty()) {

                    Contact contact = new Contact(name, phone);
                    contactAdapter.updateContact(contactID,contact);

                    CustomBaseAdapter contactArrayAdapter = new CustomBaseAdapter(getApplicationContext(),
                            contactAdapter.getContactsList());

                    if(name != finalContactName || phone != finalContactPhone) {
                        Toast.makeText(EditContactActivity.this, "Endring ble lagret.",
                                Toast.LENGTH_LONG).show();
                    }

                    startActivity(toContactsIntent);

                }
                else {
                    Toast.makeText(EditContactActivity.this, " Du må fylle ut Navn og Telefon nummer.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        Intent addToContactsIntent = new Intent(this, ContactActivity.class);


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

                    CustomBaseAdapter contactArrayAdapter = new CustomBaseAdapter(getApplicationContext(),
                            contactAdapter.getContactsList());

                    contactArrayAdapter.notifyDataSetChanged();


                    if(name != finalContactName || phone != finalContactPhone) {
                        Toast.makeText(EditContactActivity.this, "Endring ble lagret.",
                                Toast.LENGTH_LONG).show();
                    }

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

                                CustomBaseAdapter contactArrayAdapter = new CustomBaseAdapter(getApplicationContext(),
                                        contactAdapter.getContactsList());

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


