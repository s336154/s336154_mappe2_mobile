package com.example.s336154_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContactAdapter {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ContactAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public ContactAdapter open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CONTACTS_COLUMN_NAME, contact.getName());
        values.put(DatabaseHelper.CONTACTS_COLUMN_PHONE, contact.getPhone());
        return database.insert(DatabaseHelper.CONTACTS_TABLE_NAME, null, values);
    }

    public Cursor getAllContacts() {
        return database.query(DatabaseHelper.CONTACTS_TABLE_NAME,
                null, null, null, null, null, null);
    }

    public boolean updateContact(long id, Contact contact) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CONTACTS_COLUMN_NAME, contact.getName());
        values.put(DatabaseHelper.CONTACTS_COLUMN_PHONE, contact.getPhone());
        return database.update(DatabaseHelper.CONTACTS_TABLE_NAME, values,
                DatabaseHelper.CONTACTS_COLUMN_ID + "=" + id, null) > 0;
    }

    public boolean deleteContact(long id) {
        return database.delete(DatabaseHelper.CONTACTS_TABLE_NAME,
                DatabaseHelper.CONTACTS_COLUMN_ID + "=" + id, null) > 0;
    }
}
