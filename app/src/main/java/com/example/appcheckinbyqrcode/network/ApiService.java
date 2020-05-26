package com.example.appcheckinbyqrcode.network;


import com.example.appcheckinbyqrcode.ui.model.User;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //dang nhap
    @POST("login")
    Observable<User> loginnew(@Query("email") String email,
                              @Query("password") String password);

    // luu token firebase vao csdl
    @POST("save-notification")
    Observable<String> savetokenfirebase(@Query("token") String token);

}
