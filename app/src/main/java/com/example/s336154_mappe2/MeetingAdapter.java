package com.example.s336154_mappe2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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


    public boolean deleteMeeting(long meetingId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DatabaseHelper.MEETINGS_COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(meetingId) };

        int deletedRows = db.delete(dbHelper.MEETINGS_TABLE_NAME, whereClause, whereArgs);
        db.close();

        return deletedRows > 0; // Returns true if at least one row was deleted
    }

    private Meeting cursorToMeeting(Cursor cursor) {
        Meeting meeting = new Meeting();

        meeting.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.MEETINGS_COLUMN_ID)));
        meeting.setTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MEETINGS_COLUMN_TIME)));
        meeting.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MEETINGS_COLUMN_DATE)));
        meeting.setPlace(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MEETINGS_COLUMN_PLACE)));
        meeting.setComment(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MEETINGS_COLUMN_COMMENT)));
    //    meeting.setContactId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.MEETINGS_COLUMN_CONTACT_ID)));
        return meeting;
    }

    public List<Meeting> getMeetingsList() {

        List<Meeting> meetings = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.MEETINGS_TABLE_NAME, null,
                null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Meeting meeting = cursorToMeeting(cursor);
            meetings.add(meeting);
            cursor.moveToNext();
        }
        cursor.close();
        return meetings;
    }



    public long insertMeeting(Meeting meeting) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEETINGS_COLUMN_TIME, meeting.getTime());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DATE, meeting.getDate());
        values.put(DatabaseHelper.MEETINGS_COLUMN_PLACE, meeting.getPlace());
        values.put(DatabaseHelper.MEETINGS_COLUMN_COMMENT, meeting.getComment());
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
        values.put(DatabaseHelper.MEETINGS_COLUMN_COMMENT, meeting.getComment());
        values.put(DatabaseHelper.MEETINGS_COLUMN_CONTACT_ID, meeting.getContactId());
        return database.update(DatabaseHelper.MEETINGS_TABLE_NAME, values,
                DatabaseHelper.MEETINGS_COLUMN_ID + "=" + id, null) > 0;
    }

    public long getContactIdForMeeting(long meetingId) {

        long contactId = 0;  // Default value in case of an error

        String query = " SELECT " +DatabaseHelper.MEETINGS_COLUMN_CONTACT_ID+ " FROM "
                       +DatabaseHelper.MEETINGS_TABLE_NAME+ " WHERE "
                +DatabaseHelper.MEETINGS_COLUMN_ID+ " = ?";

        String[] selectionArgs = {String.valueOf(meetingId)};

        Cursor cursor = database.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            contactId = cursor.getLong(0);
        }

        cursor.close();
        return contactId;
    }


    public List<Long> getMeetingIdsWithTodayDate(Context context) {

        List<Long> matchingIds = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormat.format(new Date());

        String query = "SELECT " +DatabaseHelper.MEETINGS_COLUMN_ID+ " FROM "
                + DatabaseHelper.MEETINGS_TABLE_NAME + " WHERE "
                +DatabaseHelper.MEETINGS_COLUMN_DATE+ " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{todayDate});

        if (cursor.moveToFirst()) {
            do {
                long meetingId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.MEETINGS_COLUMN_ID));
                matchingIds.add(meetingId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return matchingIds;
    }


}

