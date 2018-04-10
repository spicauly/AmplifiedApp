package com.example.acer.amplified.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.acer.amplified.User;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_FIRST_NAME = "comments";
    public static final String DATABASE_NAME_EXTENSION = ".db";
    public static final String DATABASE_NAME = DATABASE_FIRST_NAME + DATABASE_NAME_EXTENSION;
    public static final int DATABASE_VERSION = 2;

    // Creating the table
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + CommentContract.CommentEntry.TABLE_NAME +
                    "(" +
                    CommentContract.CommentEntry._ID + " INTEGER PRIMARY KEY, " +
                    CommentContract.CommentEntry.COLUMN_NAME_COMMENT +
                    ");";

    private String CREATE_USER_TABLE = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + "("
            + UserContract.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UserContract.UserEntry.COLUMN_USER_NAME + " TEXT," + UserContract.UserEntry.COLUMN_USER_EMAIL
            + " TEXT," + UserContract.UserEntry.COLUMN_USER_PASSWORD + " TEXT" + ")";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Execute the sql to create the table comments
        database.execSQL(DATABASE_CREATE);
        database.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CommentContract.CommentEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);


    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_USER_NAME, user.getName());
        values.put(UserContract.UserEntry.COLUMN_USER_EMAIL, user.getEmail());
        values.put(UserContract.UserEntry.COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String email){
        String[] columns = {
                UserContract.UserEntry.COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = UserContract.UserEntry.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password){
        String[] columns = {
                UserContract.UserEntry.COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = UserContract.UserEntry.COLUMN_USER_EMAIL + " = ?" + " AND " + UserContract.UserEntry.COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }
}
