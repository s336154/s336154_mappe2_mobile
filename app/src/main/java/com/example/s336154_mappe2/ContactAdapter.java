package com.example.s336154_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<Contact> getContactsList() {

        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.CONTACTS_TABLE_NAME, null,
                null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        { Contact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }



    private Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();

        contact.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.CONTACTS_COLUMN_ID)));
        contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CONTACTS_COLUMN_NAME)));
        contact.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CONTACTS_COLUMN_PHONE)));
        return contact;
    }

    public boolean updateContact(long id, Contact contact) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CONTACTS_COLUMN_NAME, contact.getName());
        values.put(DatabaseHelper.CONTACTS_COLUMN_PHONE, contact.getPhone());
        return database.update(dbHelper.CONTACTS_TABLE_NAME, values,
                DatabaseHelper.CONTACTS_COLUMN_ID + "=" + id, null) > 0;
    }



    public boolean deleteContact(long contactId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DatabaseHelper.CONTACTS_COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(contactId) };

        int deletedRows = db.delete(dbHelper.CONTACTS_TABLE_NAME, whereClause, whereArgs);
        db.close();

        return deletedRows > 0; // Returns true if at least one row was deleted
    }



    public String getContactName(long contactId) {
        String name = null;  // Default value in case of an error

        String query = "SELECT " + DatabaseHelper.CONTACTS_COLUMN_NAME + " FROM " +
                DatabaseHelper.CONTACTS_TABLE_NAME + " WHERE " +
                DatabaseHelper.CONTACTS_COLUMN_ID + " = ?";

        String[] selectionArgs = {String.valueOf(contactId)};

        Cursor cursor = null; // Initialize the cursor to null

        try {
            cursor = database.rawQuery(query, selectionArgs);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_NAME);
                name = cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return name;
    }

    public boolean idExist(long contactId){
        String query = "SELECT " + DatabaseHelper.CONTACTS_COLUMN_NAME + " FROM " +
                DatabaseHelper.CONTACTS_TABLE_NAME + " WHERE " +
                DatabaseHelper.CONTACTS_COLUMN_ID + " = ?";

        String[] selectionArgs = {String.valueOf(contactId)};

        Cursor cursor = null; // Initialize the cursor to null

        try {
            cursor = database.rawQuery(query, selectionArgs);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_NAME);
                return true;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return false;

    }

    public String getContactPhone(long contactId) {
        String phone = null;  // Default value in case of an error

        String query = "SELECT " + DatabaseHelper.CONTACTS_COLUMN_PHONE + " FROM " +
                DatabaseHelper.CONTACTS_TABLE_NAME + " WHERE " +
                DatabaseHelper.CONTACTS_COLUMN_ID + " = ?";

        String[] selectionArgs = {String.valueOf(contactId)};

        Cursor cursor = null;

        try {
            cursor = database.rawQuery(query, selectionArgs);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_PHONE);
                phone = cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return phone;
    }





}

