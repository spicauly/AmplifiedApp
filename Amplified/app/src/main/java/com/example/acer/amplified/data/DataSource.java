package com.example.acer.amplified.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

    private SQLiteDatabase mDatabase;
    private MySQLiteHelper mDBHelper;
    private Context mContext;
    private String[] COMMENT_ALL_COLUMNS = {CommentContract.CommentEntry._ID,
            CommentContract.CommentEntry.COLUMN_NAME_COMMENT};


    public DataSource(Context context) {
        mContext = context;
        mDBHelper = new MySQLiteHelper(mContext);
    }

    // Opens the mDatabase to use it
    public void open() throws SQLException {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    // Closes the mDatabase when you no longer need it
    public void close() {
        mDBHelper.close();
    }

    public void createComment(String reminderText) {
        ContentValues values = new ContentValues();
        values.put(CommentContract.CommentEntry.COLUMN_NAME_COMMENT, reminderText);
        mDatabase.insert(CommentContract.CommentEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllComments() {
        return mDatabase.query(CommentContract.CommentEntry.TABLE_NAME,
                COMMENT_ALL_COLUMNS, null, null, null, null, null);
    }

    public void deleteComment(long id) {
        mDatabase.delete(CommentContract.CommentEntry.TABLE_NAME, CommentContract.CommentEntry._ID + " =?",
                new String[]{Long.toString(id)});

    }

    public void updateComment(long id, String name) {
        ContentValues args = new ContentValues();
        args.put(CommentContract.CommentEntry.COLUMN_NAME_COMMENT, name);

        mDatabase.update(CommentContract.CommentEntry.TABLE_NAME, args, CommentContract.CommentEntry._ID + "=?",
                new String[]{Long.toString(id)});
    }

    public Cursor getOneComment(long id) {
        return mDatabase.query(CommentContract.CommentEntry.TABLE_NAME, COMMENT_ALL_COLUMNS, CommentContract.CommentEntry._ID + " =?", new String[]{Long.toString(id)}, null, null, null);
    }
}
