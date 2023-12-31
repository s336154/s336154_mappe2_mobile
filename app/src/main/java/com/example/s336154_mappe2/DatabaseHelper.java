package com.example.s336154_mappe2;
import static android.app.DownloadManager.COLUMN_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "ContactsAndMeetings.db";
    private static final int DATABASE_VERSION = 2;

    // Define table and column names for Contacts
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_PHONE = "phone";

    // Define table and column names for Meetings
    public static final String MEETINGS_TABLE_NAME = "meetings";
    public static final String MEETINGS_COLUMN_ID = "id";
    public static final String MEETINGS_COLUMN_TIME = "time";
    public static final String MEETINGS_COLUMN_DATE = "date";
    public static final String MEETINGS_COLUMN_PLACE = "place";
    public static final String MEETINGS_COLUMN_COMMENT = "comment";
    public static final String MEETINGS_COLUMN_CONTACT_ID = "contact_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Contacts table
        db.execSQL("CREATE TABLE " + CONTACTS_TABLE_NAME + " (" +
                CONTACTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTACTS_COLUMN_NAME + " TEXT, " +
                CONTACTS_COLUMN_PHONE + " TEXT)");

        // Create Meetings table
        db.execSQL("CREATE TABLE " + MEETINGS_TABLE_NAME + " (" +
                MEETINGS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MEETINGS_COLUMN_TIME + " TEXT, " +
                MEETINGS_COLUMN_DATE + " TEXT, " +
                MEETINGS_COLUMN_PLACE + " TEXT, " +
                MEETINGS_COLUMN_COMMENT + " TEXT, " +
                MEETINGS_COLUMN_CONTACT_ID + " INTEGER, " +
                "FOREIGN KEY (" + MEETINGS_COLUMN_CONTACT_ID + ") " +
                "REFERENCES " + CONTACTS_TABLE_NAME + " (" + CONTACTS_COLUMN_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the database if necessary
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MEETINGS_TABLE_NAME);
        onCreate(db);
    }






}
