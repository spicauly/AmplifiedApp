package com.example.acer.amplified.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.amplified.adapters.CommentAdapter;
import com.example.acer.amplified.Comments;
import com.example.acer.amplified.data.DataSource;
import com.example.acer.amplified.R;

public class Comment extends AppCompatActivity implements CommentAdapter.CommentClickListener {

    ImageView imPicture2;
    Button bSend;
    TextView tvComments;
    private RecyclerView rvComments;
    private CommentAdapter mAdapter;
    private EditText etComment;
    private Cursor mCursor;
    private DataSource mDataSource;

    public static final String COMMENT_POSITION = "Position";
    final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        etComment = findViewById(R.id.etComment);
        mDataSource = new DataSource(this);
        mDataSource.open();

        rvComments = findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        imPicture2 = findViewById(R.id.imPicture2);
        bSend = findViewById(R.id.bSend);

        etComment.setOnEditorActionListener(new DoneOnEditorActionListener());

        tvComments = findViewById(R.id.tvComments);


        updateUI();

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard();
                String text = etComment.getText().toString();

                Comments newComment = new Comments(text);

                //Check if some text has been added
                if (!(TextUtils.isEmpty(text))) {
                    //Add the text to the list (datamodel)
                    mDataSource.createComment(text);
                    //Tell the adapter that the data set has been modified: the screen will be refreshed.
                    updateUI();

                    //Initialize the EditText for the next item
                    etComment.setText("");
                } else {
                    //Show a message to the user if the textfield is empty
                    Snackbar.make(view, "Please enter some text in the text field", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                Toast.makeText(Comment.this, "Comment send", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }


    private void updateUI() {
        mCursor = mDataSource.getAllComments();
        if (mAdapter == null) {
            mAdapter = new CommentAdapter(this, mCursor);
            rvComments.setAdapter(mAdapter);
        } else {
            mAdapter.swapCursor(mCursor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        mDataSource.open();
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCursor != null && !mCursor.isClosed()) mCursor.close();
        mDataSource.close();
    }

    @Override
    public void commentOnClick(long id) {


    }

    @Override
    public void commentOnLongClick(final long id) {
        /**Toast.makeText(getApplicationContext(), "Comment deleted", Toast.LENGTH_LONG).show();
        mDataSource.deleteComment(id);
        updateUI();**/

        final CharSequence[] items = {"Edit", "Delete"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Select The Action");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(item == 0)
                {
                    Intent intent = new Intent(Comment.this, UpdateComment.class);
                    intent.putExtra(COMMENT_POSITION, id);
                    startActivity (intent);
                }

                if(item == 1)
                {
                    Toast.makeText(getApplicationContext(), "Comment deleted", Toast.LENGTH_LONG).show();
                    mDataSource.deleteComment(id);
                    updateUI();
                }
            }
        });
        builder.show();
    }

}
