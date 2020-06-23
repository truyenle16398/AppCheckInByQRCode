package com.example.appcheckinbyqrcode.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EventFavoriteResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("checkin")
    @Expose
    private Object checkin;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("chairman")
    @Expose
    private String chairman;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("event_id")
    @Expose
    private Integer eventId;
    @SerializedName("code")
    @Expose
    private String code;

}