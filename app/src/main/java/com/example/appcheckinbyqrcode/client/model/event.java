package com.example.appcheckinbyqrcode.client.model;

import android.widget.ImageView;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
public class event implements Serializable {
    private String eventname;
    private String eventnote;
    private String eventday;
    private String eventtime;
    private String eventaddress;
    private String eventimage;

    public event(String eventname, String eventnote, String eventday, String eventtime, String eventaddress, String eventimage) {
        this.eventname = eventname;
        this.eventnote = eventnote;
        this.eventday = eventday;
        this.eventtime = eventtime;
        this.eventaddress = eventaddress;
        this.eventimage = eventimage;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventnote() {
        return eventnote;
    }

    public void setEventnote(String eventnote) {
        this.eventnote = eventnote;
    }

    public String getEventday() {
        return eventday;
    }

    public void setEventday(String eventday) {
        this.eventday = eventday;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    public String getEventaddress() {
        return eventaddress;
    }

    public void setEventaddress(String eventaddress) {
        this.eventaddress = eventaddress;
    }

    public String getEventimage() {
        return eventimage;
    }

    public void setEventimage(String eventimage) {
        this.eventimage = eventimage;
    }
}
