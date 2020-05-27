package com.example.appcheckinbyqrcode.network;

import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.ui.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    //dang nhap
    @POST("login")
    Observable<User> loginnew(@Query("email") String email,
                              @Query("password") String password);
    //logout
    @GET("logout")
    Observable<MessageResponse> logout();

    //xac nhan email de lay lai mat khau
    @POST("create")//forgot-password
    @FormUrlEncoded
    Observable<MessageResponse> forgetPass(@Field("email") String email);

    //reset pass
    @POST("reset")
    Observable<MessageResponse> resetPass(@Query("email") String email,
                                            @Query("password") String password,
                                            @Query("password_confirmation") String password_confirmation,
                                            @Query("token") String token);

    //dang ky
    @POST("register")
    Observable<User> register(@Query("email") String email,
                              @Query("name") String name,
                              @Query("address") String address,
                              @Query("phone") String phone,
                              @Query("password") String password,
                              @Query("password_confirmation") String confirmpassword);

    //show info user
    @GET("user")
    Observable<userResponse> showinfo();

    //reset pass
    @PUT("update_info")
    Observable<userResponse> update_info(@Query("name") String name,
                                            @Query("email") String email,
                                            @Query("phone") String phone,
                                            @Query("address") String address);
}
