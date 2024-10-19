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

    public databaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, EMAIL TEXT, PASSWORD TEXT)");

        {
            // Create users table
            db.execSQL("CREATE TABLE " + USERS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, EMAIL TEXT, PASSWORD TEXT)");

            // Create subjects table
            db.execSQL("CREATE TABLE " + SUBJECTS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)");

            // Create grades table
            db.execSQL("CREATE TABLE " + GRADES_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SUBJECT_ID INTEGER, GRADE_NAME TEXT, GRADE_VALUE INTEGER, FOREIGN KEY(SUBJECT_ID) REFERENCES " + SUBJECTS_TABLE + "(ID))");
    }}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

       {
            db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SUBJECTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + GRADES_TABLE);
            onCreate(db);
        }
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



        private static final String USERS_TABLE = "users";
        private static final String SUBJECTS_TABLE = "subjects";
        private static final String GRADES_TABLE = "grades";

        // Column names for subjects and grades tables
        private static final String COL_SUBJECT_ID = "ID";
        private static final String COL_SUBJECT_NAME = "NAME";

        private static final String COL_GRADE_ID = "ID";
        private static final String COL_GRADE_SUBJECT_ID = "SUBJECT_ID";
        private static final String COL_GRADE_NAME = "GRADE_NAME";
        private static final String COL_GRADE_VALUE = "GRADE_VALUE";


        // Method to insert a subject into the subjects table
        public boolean insertSubject(String subjectName) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_SUBJECT_NAME, subjectName);
            long result = db.insert(SUBJECTS_TABLE, null, contentValues);
            return result != -1;
        }

        // Method to insert grades for a subject
        public boolean insertGrade(int subjectId, String gradeName, int gradeValue) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_GRADE_SUBJECT_ID, subjectId);
            contentValues.put(COL_GRADE_NAME, gradeName);
            contentValues.put(COL_GRADE_VALUE, gradeValue);
            long result = db.insert(GRADES_TABLE, null, contentValues);
            return result != -1;
        }

        // Method to retrieve all subjects
        public Cursor getAllSubjects() {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT * FROM " + SUBJECTS_TABLE, null);
        }

        // Method to retrieve grades for a specific subject by subject ID
        public Cursor getGradesBySubject(int subjectId) {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT * FROM " + GRADES_TABLE + " WHERE SUBJECT_ID = ?", new String[]{String.valueOf(subjectId)});
        }
    }



