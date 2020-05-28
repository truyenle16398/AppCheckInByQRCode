package com.example.appcheckinbyqrcode.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EventListResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("intro")
    @Expose
    private String intro;

    @SerializedName("chairman")
    @Expose
    private String chairman;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("place")
    @Expose
    private String place;

}
