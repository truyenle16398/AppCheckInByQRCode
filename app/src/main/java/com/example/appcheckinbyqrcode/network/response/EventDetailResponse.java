package com.example.appcheckinbyqrcode.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EventDetailResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("intro")
    @Expose
    private String intro;

    @SerializedName("detail")
    @Expose
    private String detail;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    @SerializedName("chairman")
    @Expose
    private String chairman;

    @SerializedName("max_register")
    @Expose
    private String max_register;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("place")
    @Expose
    private String place;

}
