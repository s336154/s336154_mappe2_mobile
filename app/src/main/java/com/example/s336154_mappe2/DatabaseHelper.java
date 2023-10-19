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

    public long getContactIdByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + CONTACTS_TABLE_NAME +
                " WHERE " + CONTACTS_COLUMN_NAME + " = ?", new String[]{name});

        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        return -1; // Contact not found
    }

    public String getContactNameByID (long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + CONTACTS_COLUMN_NAME + " FROM " + CONTACTS_TABLE_NAME +
                " WHERE " + CONTACTS_COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return null; // Contact not found
    }

    public String getContactPhoneByID (long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + CONTACTS_COLUMN_PHONE + " FROM " + CONTACTS_TABLE_NAME +
                " WHERE " + CONTACTS_COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return null; // Contact not found
    }

    public long getContactIdForMeeting(long meetingId) {
        SQLiteDatabase db = this.getReadableDatabase();
        long contactId = -1;  // Default value in case of an error

        String query = "SELECT contact_id FROM meetings WHERE id = ?";
        String[] selectionArgs = {String.valueOf(meetingId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            contactId = cursor.getLong(0);  // Assumes contact_id is in the first column
        }

        cursor.close();
        return contactId;
    }

    public void deleteContact(long contactId) {
        SQLiteDatabase db = this.getWritableDatabase(); // Open a writable database connection

        // Define the table name and the condition for deletion (e.g., by ID)
        String tableName = "contacts";
        String condition = "id = ?"; // Modify this condition based on your criteria
        String[] selectionArgs = {String.valueOf(contactId)}; // The value to match in the condition

        // Execute the delete query
        db.delete(tableName, condition, selectionArgs);

        db.close(); // Close the database connection
    }


    public long insertContact(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACTS_COLUMN_NAME, name);
        return db.insert(CONTACTS_TABLE_NAME, null, values);
    }

    public Cursor getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + CONTACTS_TABLE_NAME, null);
    }


    public boolean deleteContactById(long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRowsDeleted = db.delete(CONTACTS_TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(contactId)});
        return numRowsDeleted > 0;
    }

    public void deleteAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CONTACTS_TABLE_NAME);
    }

    public ArrayList<String> getItemsFromColumnName() {
        ArrayList<String> items = new ArrayList<>();

        // Open the database for reading
        SQLiteDatabase database = this.getReadableDatabase();

        // Specify the table and column you want to retrieve data from
        String tableName = "CONTACTS_TABLE_NAME";
        String columnName = "CONTACTS_COLUMN_NAME";

        // Query the database to get the items from the specified column
        Cursor cursor = database.query(tableName, new String[]{columnName}, null,
                null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int int_cursor = cursor.getColumnIndex(columnName);
                    // Retrieve the data from the specified column
                    String item = cursor.getString(int_cursor);
                    items.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return items;
    }

    public ArrayList<String> getItemsFromColumnPhone() {
        ArrayList<String> items = new ArrayList<>();

        // Open the database for reading
        SQLiteDatabase database = this.getReadableDatabase();

        // Specify the table and column you want to retrieve data from
        String tableName = "CONTACTS_TABLE_NAME";
        String columnName = "CONTACTS_COLUMN_PHONE";

        // Query the database to get the items from the specified column
        Cursor cursor = database.query(tableName, new String[]{columnName}, null,
                null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int int_cursor = cursor.getColumnIndex(columnName);
                    // Retrieve the data from the specified column
                    String item = cursor.getString(int_cursor);
                    items.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return items;
    }






}
