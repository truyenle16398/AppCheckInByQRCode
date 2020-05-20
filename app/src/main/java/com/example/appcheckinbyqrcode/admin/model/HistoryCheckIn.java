package com.example.appcheckinbyqrcode.admin.model;

public class HistoryCheckIn {
    private String mName, mImage, mTimeCheck;

    public HistoryCheckIn(String mName, String mImage, String mTimeCheck) {
        this.mName = mName;
        this.mImage = mImage;
        this.mTimeCheck = mTimeCheck;
    }

    public HistoryCheckIn() {
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmTimeCheck() {
        return mTimeCheck;
    }

    public void setmTimeCheck(String mTimeCheck) {
        this.mTimeCheck = mTimeCheck;
    }
}
