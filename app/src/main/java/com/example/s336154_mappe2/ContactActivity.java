package com.example.s336154_mappe2;

import static com.example.s336154_mappe2.DatabaseHelper.CONTACTS_COLUMN_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class ContactActivity extends AppCompatActivity {

    private ContactAdapter contactAdapter;
    private List<Contact> contactsList;
    private ArrayAdapter<Contact> contactArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();

        EditText et = (EditText) findViewById(R.id.text);


        // Implement onClick listeners for save and delete buttons
        Button saveContactButton = findViewById(R.id.addContactButton);
        Button deleteContactButton = findViewById(R.id.deleteContactButton);
        Button editContact = findViewById(R.id.editContact);
        Button toMainActivity = findViewById(R.id.toMainButton);

        ListView contactListView = (ListView) findViewById(R.id.listView);
        contactsList = (List<Contact>) contactAdapter.getContactsList();


        contactArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsList);
        contactListView.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();


        Intent addContactsIntent = new Intent(this, AddContactActivity.class);
        Intent toMainActivityIntent = new Intent(this, MainActivity.class);
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addContactsIntent);
            }
        });

        toMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toMainActivityIntent);
            }
        });




        //    Intent deleteContactsIntent =new Intent(this, DeleteContactActivity.class);


        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact selectedItem = (Contact) parent.getItemAtPosition(position);
                Toast.makeText(ContactActivity.this, "Trykket på :  " + selectedItem.getName(),
                        Toast.LENGTH_LONG).show();

                Log.d("Selected contact ID", "Name selected is: " + String.valueOf(selectedItem.getName()) +
                        "  ID is: " + String.valueOf(selectedItem.getId()));

                String nameDeleted = selectedItem.getName();
                long contactId = selectedItem.getId();

                Log.d("contactId", "ID is: " + String.valueOf(contactId));

                deleteContactButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(deleteContactButton.getContext());
                        builder.setMessage("Vil du slette " +nameDeleted+ " ?")
                                .setPositiveButton("Slett", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Perform the contact deletion here
                                        contactArrayAdapter.remove(contactArrayAdapter.getItem(position));
                                        contactArrayAdapter.notifyDataSetChanged();

                                        contactAdapter.deleteContact(selectedItem.getId());
                                        Toast.makeText(ContactActivity.this, nameDeleted + " er slettet fra kontakt liste.",
                                                    Toast.LENGTH_LONG).show();
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

                editContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                 //       addContactMeetingIntent.putExtra("contactId", contactId);
                        //      startActivity(addContactMeetingIntent);
                    }
                });
            }
        });
        }

    @Override
    protected void onResume() {
        super.onResume();
        contactAdapter.open();
    }
    @Override
    protected void onPause() {
        super.onPause();
        contactAdapter.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactAdapter.close();
    }


 }




                /*    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedContactName = String.valueOf(contactArrayAdapter.getItem(position));
                        long contactId = dbHelper.getContactIdByName(selectedContactName);

                        if (contactId != -1) {
                            boolean contactDeleted = dbHelper.deleteContactById(contactId);

                            if (contactDeleted) {
                                // Contact was successfully deleted
                                contactAdapter.deleteContactById(contactId);
                            } else {
                                // Contact deletion failed
                            }
                        } else {
                            // Contact not found
                        }
                        dbHelper.close();
                    }

                 */





