package com.example.s336154_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MeetingAdapter {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public MeetingAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public MeetingAdapter open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

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

    public boolean updateMeeting(long id, Meeting meeting) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEETINGS_COLUMN_TIME, meeting.getTime());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DATE, meeting.getDate());
        values.put(DatabaseHelper.MEETINGS_COLUMN_PLACE, meeting.getPlace());
        values.put(DatabaseHelper.MEETINGS_COLUMN_CONTACT_ID, meeting.getContactId());
        return database.update(DatabaseHelper.MEETINGS_TABLE_NAME, values,
                DatabaseHelper.MEETINGS_COLUMN_ID + "=" + id, null) > 0;
    }

    public boolean deleteMeeting(long id) {
        return database.delete(DatabaseHelper.MEETINGS_TABLE_NAME,
                DatabaseHelper.MEETINGS_COLUMN_ID + "=" + id, null) > 0;
    }
}
