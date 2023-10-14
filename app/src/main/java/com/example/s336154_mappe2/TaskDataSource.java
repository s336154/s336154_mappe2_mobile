package com.example.s336154_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    public TaskDataSource(Context context) { dbHelper = new DatabaseHelper(context); }
    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }
    public void close() { dbHelper.close(); }
    public Task addTask(String taskName, String phoneNr) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TASK_NAME, taskName);
        values.put(DatabaseHelper.COLUMN_TASK_PHONENR, phoneNr);
        long insertId = database.insert(DatabaseHelper.TABEL_TASKS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABEL_TASKS, null,
                DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }
    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();

        task.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));

        task.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_NAME)));
        task.setPhonenr(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_PHONENR)));
        return task;
    }

    public List<Task> findAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABEL_TASKS, null,
                null, null, null, null, null); cursor.moveToFirst();
        while (!cursor.isAfterLast()) { Task task = cursorToTask(cursor);
            tasks.add(task); cursor.moveToNext(); }
        cursor.close(); return tasks;
    }
    public void deleteTask(long taskId) {
        database.delete(DatabaseHelper.TABEL_TASKS, DatabaseHelper.COLUMN_ID + " =? ", new
                String[]{Long.toString(taskId)});
    }
}