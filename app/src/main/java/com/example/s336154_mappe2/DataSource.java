package com.example.s336154_mappe2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Contact methods
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

    // Meeting methods
    public long insertMeeting(Meeting meeting) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEETINGS_COLUMN_TIME, meeting.getTime());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DATE, meeting.getDate());
        values.put(DatabaseHelper.MEETINGS_COLUMN_PLACE, meeting.getPlace());
        values.put(DatabaseHelper.MEETINGS_COLUMN_CONTACT_ID, meeting.getContactId());
        return database.insert(DatabaseHelper.MEETINGS_TABLE_NAME, null, values);
    }

    public Cursor getAllMeetings() {
        return database.query(DatabaseHelper.MEETINGS_TABLE_NAME,
                null, null, null, null, null, null);


    }
}
