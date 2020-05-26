package com.example.appcheckinbyqrcode.network;


import com.example.appcheckinbyqrcode.network.response.forgetPassResponse;
import com.example.appcheckinbyqrcode.network.response.resetPassResponse;
import com.example.appcheckinbyqrcode.ui.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //dang nhap
    @POST("login")
    Observable<User> loginnew(@Query("email") String email,
                              @Query("password") String password);

    //xac nhan email de lay lai mat khau
    @POST("create")//forgot-password
    @FormUrlEncoded
    Observable<forgetPassResponse> forgetPass(@Field("email") String email);

    //reset pass
    @POST("reset")
    Observable<resetPassResponse> resetPass(@Query("email") String email,
                                            @Query("password") String password,
                                            @Query("password_confirmation") String password_confirmation,
                                            @Query("token") String token);






    // luu token firebase vao csdl
    @POST("save-notification")
    Observable<String> savetokenfirebase(@Query("token") String token);

    //dang ky
    @POST("register")
    Observable<User> register(@Query("email") String email,
                              @Query("name") String name,
                              @Query("address") String address,
                              @Query("phone") String phone,
                              @Query("password") String password,
                              @Query("password_confirmation") String confirmpassword);
}
