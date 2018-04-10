package com.example.acer.amplified.data;

import android.provider.BaseColumns;

public final class CommentContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private CommentContract() {}

    /* Inner class that defines the table contents */
    public static class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Comments";
        public static final String COLUMN_NAME_COMMENT = "comment";

    }
}

