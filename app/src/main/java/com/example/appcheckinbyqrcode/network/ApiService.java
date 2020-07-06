package com.example.appcheckinbyqrcode.network;

import com.example.appcheckinbyqrcode.network.response.EventDetailResponse;
import com.example.appcheckinbyqrcode.network.response.EventFavoriteResponse;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.network.response.EventSearchListResponse;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.response.UploadAvatarResponse;
import com.example.appcheckinbyqrcode.network.response.UserResponse;
import com.example.appcheckinbyqrcode.ui.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    @POST("update_info")
    Observable<UserResponse> updateinfo(@Query("email") String email,
                                        @Query("name") String name,
                                        @Query("phone") String phone,
                                        @Query("address") String address);

    //reset AVATER
//    @POST("UploadAvatar")
//    Observable<UploadAvatarResponse> updateAvatar(@Query("name") String name,
//                                                  @Query("email") String email,
//                                                  @Query("phone") String phone,
//                                                  @Query("address") String address,
//                                                  @Query("avatar") String avatar,
//                                                  @Query("role_id") String role_id,
//                                                  @Query("status") String status,
//                                                  @Query("created_at") String created_at,
//                                                  @Query("updated_at") String updated_at);
    //thay doi image
    @Multipart
    @POST("UploadAvatar")
    Observable<UploadAvatarResponse> updateAvatar(@Part MultipartBody.Part avatar);

    //doi mat khau
    @POST("update_pass")
    Observable<MessageResponse> updatepass(@Query("old_pass") String oldpass,
                                           @Query("new_pass") String newpass);

    //show danh sach su kien danh sách sự kiện sắp diễn ra going to happen (tuong lai)
    @GET("event/list")
    Observable<List<EventListResponse>> listlevents();

    //show danh sách sự kiện đang diễn ra going on (hien tai)
    @GET("event/list_happen")
    Observable<List<EventListResponse>> listleventgoingon();

    //show danh sách sự kiện đã diễn ra happened (qua khu)
    @GET("event/list_happened")
    Observable<List<EventListResponse>> listleventhappened();


    //search view theo name event
    @GET("search")
    Observable<List<EventSearchListResponse>> getListSearch(
            @Query("key") String keyword
    );



    // show lịch sử đăng ký sự kiện
    @GET("event/history_regis")
    Observable<List<EventFavoriteResponse>> listhistoryregis();

    //show chi tiet danh sach su kien
    @GET("event/detail/{id}")
    Observable<MessageResponse> detailevents(@Path("id") int id);

    //đăng kí sự kiện
    @POST("event/register/{id}")
    Observable<MessageResponse> registerevent(@Path("id") int id);

    //Hủy sự kiện
    @POST("event/cancel/{id}")
    Observable<MessageResponse> cancelevent(@Path("id") int id);

    //Quét qr
    @POST("checkin/{id}")
    Observable<MessageResponse> savecheckin(@Path("id") int id,@Query("code") String code);

}
