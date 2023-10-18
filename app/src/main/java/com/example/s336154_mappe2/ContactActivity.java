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
    //   private EditText editContactName, editContactPhone;
    private List<Contact> contactsList;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<Contact> contactArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();

        EditText et = (EditText) findViewById(R.id.text);

  /*      Button dialogknapp = findViewById(R.id.dialog);
        dialogknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        }
        });

   */


        //    editContactName = findViewById(R.id.editContactName);
        //    editContactPhone = findViewById(R.id.editContactPhone);

        // Implement onClick listeners for save and delete buttons
        Button saveContactButton = findViewById(R.id.addContactButton);
        Button deleteContactButton = findViewById(R.id.deleteContactButton);
        Button addContactMeeting = findViewById(R.id.addContactMeeting);

        ListView contactListView = (ListView) findViewById(R.id.listView);
        contactsList = (List<Contact>) contactAdapter.getContactsList();


        contactArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsList);

        contactListView.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact selectedItem = (Contact) parent.getItemAtPosition(position);
                // Handle item click here
                Toast.makeText(ContactActivity.this, "Clicked on: " + selectedItem.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });


        Intent addContactsIntent = new Intent(this, AddContactActivity.class);
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addContactsIntent);
            }
        });

        Intent addContactMeetingIntent = new Intent(this, MeetingActivity.class);
        addContactMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addContactMeetingIntent);
            }
        });


        //    Intent deleteContactsIntent =new Intent(this, DeleteContactActivity.class);


        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact selectedItem = (Contact) parent.getItemAtPosition(position);
                Toast.makeText(ContactActivity.this, "Clicked on: " + selectedItem.getName(),
                        Toast.LENGTH_LONG).show();

                Log.d("Selected contact ID", "Name selected is: " + String.valueOf(selectedItem.getName()) +
                        "  ID is: " + String.valueOf(selectedItem.getId()));

                String nameDeleted = selectedItem.getName();

                deleteContactButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(deleteContactButton.getContext());
                        builder.setMessage("Slett kontakt ")
                                .setPositiveButton("Slett", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Perform the contact deletion here
                                        contactArrayAdapter.remove(contactArrayAdapter.getItem(position));
                                        contactArrayAdapter.notifyDataSetChanged();

                                        Boolean deleted = contactAdapter.deleteContact(selectedItem.getId());
                                        Toast.makeText(ContactActivity.this, "Contact " + nameDeleted + " is deleted.",
                                                    Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User clicked "Cancel," do nothing
                                        return;
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();






                    }
                });
            }
        });





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





