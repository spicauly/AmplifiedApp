package com.example.acer.amplified.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.acer.amplified.data.CommentContract;
import com.example.acer.amplified.data.DataSource;
import com.example.acer.amplified.R;


public class UpdateComment extends AppCompatActivity {

    private EditText mCommentView;


    private long mID;
    private DataSource mDataSource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Init local variables
        mCommentView = findViewById(R.id.et_updatecomment);

//Obtain the parameters provided by MainActivity
        mID = getIntent().getLongExtra(Comment.COMMENT_POSITION, -1);
//If no "position in list" can be found, the default value is -1. This could be used to recognize an issue.

        //Initiate and open DataSource
        mDataSource = new DataSource(this);
        mDataSource.open();

        //Get current comment name
        Cursor mCursor = mDataSource.getOneComment(mID);
        if (mCursor != null)
            mCursor.moveToFirst();
        mCommentView.setText(mCursor.getString(mCursor.getColumnIndex(CommentContract.CommentEntry.COLUMN_NAME_COMMENT)));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return entered data to MainActivity (if not empty, else throw a snackbar message
                String updatedCommentText = mCommentView.getText().toString();


                //(reminderUpdate.setmReminderText(updatedReminderText)));
                if (!TextUtils.isEmpty(updatedCommentText)) {


                    mDataSource.updateComment(mID, updatedCommentText);
                    mDataSource.close();

                    finish();
                } else {
                    Snackbar.make(view, "Enter some data", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}

