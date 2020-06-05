package com.example.appcheckinbyqrcode.ui.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
public class Event implements Serializable {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("event_id")
    @Expose
    private Integer eventId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;
}
