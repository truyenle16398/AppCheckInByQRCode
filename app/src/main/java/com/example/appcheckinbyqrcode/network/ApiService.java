package com.example.appcheckinbyqrcode.network;


import com.example.appcheckinbyqrcode.ui.model.User;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //dang nhap
    @FormUrlEncoded
    @POST("login")
    Single<User> loginnew(@Field("email") String email,
                          @Field("password") String password);

    // luu token firebase vao csdl
    @POST("save-notification")
    Single<String> savetokenfirebase(@Query("token") String token);

    @Headers({"Accept: application/json"
            //  , "Content-Type : application/json"
    })

    @POST("login")
    @FormUrlEncoded
    Call<User> login(@Field("email") String email,
                     @Field("password") String password);
}
