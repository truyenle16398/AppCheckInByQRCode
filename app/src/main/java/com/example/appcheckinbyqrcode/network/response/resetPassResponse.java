package com.example.appcheckinbyqrcode.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class resetPassResponse {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("password_confirmation")
    @Expose
    private String password_confirmation;

    @SerializedName("token")
    @Expose
    private String token;


    @SerializedName("message")
    @Expose
    private String message;

}
