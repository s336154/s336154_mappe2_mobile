package com.example.s336154_mappe2;

import static android.app.DownloadManager.COLUMN_ID;



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


    public Contact getContact(long contactId) {
        Cursor cursor = database.query(DatabaseHelper.CONTACTS_TABLE_NAME, null,
                DatabaseHelper.CONTACTS_COLUMN_ID + " = ?", new String[]{String.valueOf(contactId)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Contact contact = cursorToContact(cursor);
            cursor.close();
            return contact;
        } else {
            return null;
        }
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

  /*  public boolean deleteContact(long id) {
        return database.delete(DatabaseHelper.CONTACTS_TABLE_NAME,
                DatabaseHelper.CONTACTS_COLUMN_ID + "=" + id, null) > 0;
    } */

    public boolean deleteContact(long contactId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DatabaseHelper.CONTACTS_COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(contactId) };

        int deletedRows = db.delete(dbHelper.CONTACTS_TABLE_NAME, whereClause, whereArgs);
        db.close();

        return deletedRows > 0; // Returns true if at least one row was deleted
    }


    public long getContactIdByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + dbHelper.CONTACTS_TABLE_NAME +
                " WHERE " + dbHelper.CONTACTS_COLUMN_NAME + " = ?", new String[]{name});

        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        return -1; // Contact not found
    }

    public boolean deleteContactById(long contactId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numRowsDeleted = db.delete(dbHelper.CONTACTS_TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(contactId)});
        return numRowsDeleted > 0;
    }

}

/*
  public Contact getContactById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Contact resultObject = null;

        Cursor cursor = db.query(DatabaseHelper.CONTACTS_TABLE_NAME, null,
                DatabaseHelper.CONTACTS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                resultObject = new Contact();
                resultObject.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_ID)));
                resultObject.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_NAME)));
                resultObject.setPhone(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_PHONE)));
            }
            cursor.close();
        }
        db.close();

        return resultObject;
    }
 */
