package com.example.appcheckinbyqrcode.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class info {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")//name
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")//status
    @Expose
    private String status;
    @SerializedName("role_id")//role_id
    @Expose
    private String roleId;

}
