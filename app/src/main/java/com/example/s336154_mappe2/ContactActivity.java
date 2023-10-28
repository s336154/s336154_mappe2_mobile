package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
public class ContactActivity extends AppCompatActivity {

    private ContactAdapter contactAdapter;
    private List<Contact> contactsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();

        EditText et = (EditText) findViewById(R.id.text);

        Button saveContactButton = findViewById(R.id.addContactButton);
        Button editContact = findViewById(R.id.editContact);
        Button toMeetingActivity = findViewById(R.id.toMeetingButton);



        ListView contactListView = (ListView) findViewById(R.id.listView);
        contactsList = (List<Contact>) contactAdapter.getContactsList();


        CustomBaseAdapter contactArrayAdapter = new CustomBaseAdapter(this, contactsList);
        contactListView.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();


        Intent addContactsIntent = new Intent(this, AddContactActivity.class);
        Intent toMeetingActivityIntent = new Intent(this, MeetingActivity.class);
        Intent editContactsIntent = new Intent(this, EditContactActivity.class);

        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addContactsIntent);
            }
        });



        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the position of the selected item

                Contact selectedItem = (Contact) contactsList.get(position);
                Log.d("Base Selected Item", "Value is: "+String.valueOf(selectedItem));


                Log.d("Selected contact ID", "Name selected is: " + String.valueOf(selectedItem.getName()) +
                        "  ID is: " + String.valueOf(selectedItem.getId()));


                long contactId = selectedItem.getId();

                Log.d("contactId", "ID is: " + String.valueOf(contactId));


                editContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editContactsIntent.putExtra("contactId", contactId);
                        editContactsIntent.putExtra("contactName", selectedItem.getName());
                        editContactsIntent.putExtra("contactPhone",selectedItem.getPhone() );
                        editContactsIntent.putExtra("position",position );
                       startActivity(editContactsIntent);
                    }
                });

                toMeetingActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        toMeetingActivityIntent.putExtra("contactId",selectedItem.getId());
                        startActivity(toMeetingActivityIntent);
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






