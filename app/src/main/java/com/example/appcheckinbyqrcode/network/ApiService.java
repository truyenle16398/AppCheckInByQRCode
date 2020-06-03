package com.example.appcheckinbyqrcode.network;

import com.example.appcheckinbyqrcode.network.response.EventDetailResponse;
import com.example.appcheckinbyqrcode.network.response.EventFavoriteResponse;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.response.UserResponse;
import com.example.appcheckinbyqrcode.ui.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
    @POST("forgot_password")//forgot-password
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
    Observable<UserResponse> showinfo();

    //reset pass
    @PUT("update_info")
    Observable<UserResponse> updateinfo(@Query("name") String name,
                                        @Query("email") String email,
                                        @Query("phone") String phone,
                                        @Query("address") String address);

    //doi mat khau
    @PUT("update_pass")
    Observable<MessageResponse> updatepass(@Query("old_pass") String oldpass,
                                           @Query("new_pass") String newpass);
    //show danh sach su kien
    @GET("event/list")
    Observable<List<EventListResponse>> listlevents();

    // show lịch sử đăng ký sự kiện
    @GET("history_regis")
    Observable<List<EventFavoriteResponse>> listhistoryregis();

    //show chi tiet danh sach su kien
    @GET("event/detail/{id}")
    Observable<EventDetailResponse> detailevents(@Path("id") int id);

    //đăng kí sự kiện
    @POST("register_event/{id}")
    Observable<MessageResponse> registerevent(@Path("id") int id);

    //Hủy sự kiện
    @PUT("cancel_event/{id}")
    Observable<MessageResponse> cancelevent(@Path("id") int id);



}
