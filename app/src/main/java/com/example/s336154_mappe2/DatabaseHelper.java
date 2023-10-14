package com.example.s336154_mappe2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABEL_TASKS = "tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK_NAME = "name";
    public static final String COLUMN_TASK_PHONENR = "phonenr";
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " +
            TABEL_TASKS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "" + COLUMN_TASK_NAME + " TEXT NOT NULL, " + COLUMN_TASK_PHONENR + " TEXT NOT NULL)";
    public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }
    @Override public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_TABLE_TASKS); }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    } }