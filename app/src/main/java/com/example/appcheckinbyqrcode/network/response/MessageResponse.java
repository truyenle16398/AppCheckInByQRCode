package com.example.appcheckinbyqrcode.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.appcheckinbyqrcode.ui.client.model.Event;

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
}
