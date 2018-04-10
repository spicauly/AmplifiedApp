package com.example.acer.amplified.data;

import android.provider.BaseColumns;

public class UserContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}

    public static class UserEntry implements BaseColumns {

        public static final String TABLE_NAME = "user";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_EMAIL = "user_email";
        public static final String COLUMN_USER_PASSWORD = "user_password";
    }
}
