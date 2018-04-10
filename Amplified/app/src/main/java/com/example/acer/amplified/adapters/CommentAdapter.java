package com.example.acer.amplified.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.amplified.data.CommentContract;
import com.example.acer.amplified.R;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    final private CommentClickListener mCommentClickListener;
    private Cursor mCursor;

    public interface CommentClickListener {
        void commentOnClick(long id);

        void commentOnLongClick(long id);
    }

    public CommentAdapter(CommentClickListener mCommentClickListener, Cursor mCursor) {
        this.mCommentClickListener = mCommentClickListener;
        this.mCursor = mCursor;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, parent, false);

        // Return a new holder instance
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, final int position) {

        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        String name = mCursor.getString(mCursor.getColumnIndex(CommentContract.CommentEntry.COLUMN_NAME_COMMENT));

        holder.textView.setText(name);

    }

    @Override
    public int getItemCount() {
        return (mCursor == null ? 0 : mCursor.getCount());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public TextView textView;

        public ViewHolder(View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (!mCursor.moveToPosition(clickedPosition))
                return false; // bail if returned null
            // Update the view holder with the information needed to display
            final long id =
                    mCursor.getLong(mCursor.getColumnIndex(CommentContract.CommentEntry._ID));
            mCommentClickListener.commentOnLongClick(id);
            return true;
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (!mCursor.moveToPosition(clickedPosition))
                return; // bail if returned null
            // Update the view holder with the information needed to display
            final long id =
                    mCursor.getLong(mCursor.getColumnIndex(CommentContract.CommentEntry._ID));
            mCommentClickListener.commentOnClick(id);
        }
    }

    public void swapCursor(Cursor newCursor) {

        if (mCursor != null) mCursor.close();

        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }
}
