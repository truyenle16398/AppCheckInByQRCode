package com.example.appcheckinbyqrcode.ui.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//@Data
//@EqualsAndHashCode(callSuper = false)
public class InfoQR {
    public String email;
    public String name;
    public String avatar;
    public String phone;
    public String address;
    public String timecheckin;

    public InfoQR(int id, String email, String name, String avatar, String phone, String address, String timecheckin) {
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        this.timecheckin = timecheckin;

    }
}
