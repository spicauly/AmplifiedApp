package com.example.acer.amplified;

public class Comments {

    private String mCommentText;

    public Comments(String mCommentText) {
        this.mCommentText = mCommentText;
    }

    public String getmCommentText() {
        return mCommentText;
    }

    public void setmCommentText(String mCommentText) {
        this.mCommentText = mCommentText;
    }

    @Override
    public String toString() {
        return mCommentText;
    }
}
