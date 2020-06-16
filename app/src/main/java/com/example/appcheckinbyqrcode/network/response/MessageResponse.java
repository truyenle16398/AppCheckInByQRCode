package com.example.appcheckinbyqrcode.network.response;

import com.example.appcheckinbyqrcode.ui.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.appcheckinbyqrcode.ui.client.model.Event;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class MessageResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("event")
    @Expose
    private Event event;

    @SerializedName("user")
    @Expose
    private ArrayList<UserQRRespon> user;

    @SerializedName("number_register")
    @Expose
    private String number_register;

    @SerializedName("detail")
    @Expose
    private EventDetailResponse detail;
}
