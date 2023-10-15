package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
public class ContactActivity extends AppCompatActivity {
    private ContactAdapter contactAdapter;
    private EditText editContactName, editContactPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();

        editContactName = findViewById(R.id.editContactName);
        editContactPhone = findViewById(R.id.editContactPhone);

        // Implement onClick listeners for save and delete buttons
        Button saveContactButton = findViewById(R.id.saveContactButton);
        Button deleteContactButton = findViewById(R.id.deleteContactButton);

        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the user input from editContactName and editContactPhone
                String name = editContactName.getText().toString();
                String phone = editContactPhone.getText().toString();

                // Create a new Contact object and save it to the database
                Contact contact = new Contact(name, phone);
                long contactId = contactAdapter.insertContact(contact);
            }
        });

        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement code to delete a contact
                // You can get the contact's ID and call contactAdapter.deleteContact(contactId)
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactAdapter.close();
    }
}
