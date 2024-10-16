package com.example.taskmanagementapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FIRSTNAME";
    private static final String COL_3 = "LASTNAME";
    private static final String COL_4 = "EMAIL";
    private static final String COL_5 = "PASSWORD";
// Task table
    private static final String  TASKS_TABLE_NAME="tasks";
    //Table columns
    private static final String TASK_COL_1 = "ID";
    private static final String TASK_COL_2 = "TITLE";
    private static final String TASK_COL_3 = "CATEGORY";
    private static final String TASK_COL_4 = "DATE";
    private static final String TASK_COL_5 = "TIME";
    private static final String TASK_COL_6 = "DESCRIPTION";

    public databaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, EMAIL TEXT, PASSWORD TEXT)");
        // Create the tasks table
        db.execSQL("CREATE TABLE " + TASKS_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, CATEGORY TEXT, DATE TEXT, TIME TEXT, DESCRIPTION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        onCreate(db);
    }

    public Cursor getUserData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query to retrieve user data by email with the correct column names
        return db.rawQuery("SELECT FIRSTNAME, LASTNAME FROM users WHERE EMAIL = ?", new String[]{email});
    }

    public boolean insertUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    // Insert a new task into the database
    public boolean insertTask(String title, String category, String date, String time, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_COL_2, title);
        contentValues.put(TASK_COL_3, category);
        contentValues.put(TASK_COL_4, date);
        contentValues.put(TASK_COL_5, time);
        contentValues.put(TASK_COL_6, description);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    // Fetch all tasks from the database
    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME, null);
    }
}

