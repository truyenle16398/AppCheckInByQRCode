package com.example.appcheckinbyqrcode.admin.model;

public class HistoryCheckIn {
    private String mName, mImage, mEventName, mTimeCheck;

    public HistoryCheckIn() {
    }

    public HistoryCheckIn(String mName, String mImage, String mEventName, String mTimeCheck) {
        this.mName = mName;
        this.mImage = mImage;
        this.mEventName = mEventName;
        this.mTimeCheck = mTimeCheck;
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

    public String getmEventName() {
        return mEventName;
    }

    public void setmEventName(String mEventName) {
        this.mEventName = mEventName;
    }

    public String getmTimeCheck() {
        return mTimeCheck;
    }

    public void setmTimeCheck(String mTimeCheck) {
        this.mTimeCheck = mTimeCheck;
    }
}
