package com.app.yallagame.ae.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIInterface {

    //Auth
    @FormUrlEncoded
    @POST("auth/signup")
    Call<ResponseBody> signupUser(@Field("full_name") String fullName,
                                  @Field("email") String email,
                                  @Field("username") String userName,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> userLogin(@Field("email") String email,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/verify-email")
    Call<ResponseBody> verifyEmail(@Field("token") String token,
                                   @Field("code") String code,
                                   @Field("just_verify") String justVerify);

    @FormUrlEncoded
    @POST("auth/forgot-password")
    Call<ResponseBody> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("auth/forgot-email")
    Call<ResponseBody> forgotEmail(@Field("username") String userName);

    @FormUrlEncoded
    @POST("auth/forgot-username")
    Call<ResponseBody> forgotUserName(@Field("email") String email);

    @FormUrlEncoded
    @POST("auth/reset-password")
    Call<ResponseBody> resetPassword(@Field("token") String token,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/access-token")
    Call<ResponseBody> refreshAccessToken(@Field("token") String refreshToken);


    //Profile
    @FormUrlEncoded
    @POST("user/profile/complete-profile")
    Call<ResponseBody> completeProfile(@Field("gender") String gender,
                                       @Field("date_of_birth") String dob,
                                       @Field("phone") String phone,
                                       @Field("avatar") String avatar,
                                       @Field("country") String country,
                                       @Field("games") String games);

    @GET("user/profile")
    Call<ResponseBody> getUserProfile();

    @PUT("user/profile")
    Call<ResponseBody> updateProfile(@Field("full_name") String fullName,
                                     @Field("username") String userName,
                                     @Field("email") String email,
                                     @Field("phone") String phone,
                                     @Field("gender") String gender,
                                     @Field("date_of_birth") String dob,
                                     @Field("avatar") String avatar,
                                     @Field("country") String country);

    @PUT("user/profile/games")
    Call<ResponseBody> updateUserGames(@Field("games") String games);

    @FormUrlEncoded
    @POST("user/profile/logout")
    Call<ResponseBody> logOutUser(@Field("device") String deviceId);

    @FormUrlEncoded
    @POST("user/profile/follow")
    Call<ResponseBody> followUser(@Field("follower_id") String followerId);

    @FormUrlEncoded
    @POST("user/profile/unfollow")
    Call<ResponseBody> unfollowUser(@Field("follower_id") String followerId);

    @GET("user/profile/followers")
    Call<ResponseBody> getUserFollowers();

    @GET("user/profile/followings")
    Call<ResponseBody> getUserFollowing();


    //utils
    @GET("utils/countries")
    Call<ResponseBody> getAllCountries();

    @GET("utils/avatars")
    Call<ResponseBody> getAllAvatars();

    @GET("utils/games")
    Call<ResponseBody> getGames();


    @PUT("user/profile/setting")
    Call<ResponseBody> saveUserSettings(@Field("notify_me") String isNotification,
                                        @Field("language") String language);


    //Games and Tournaments

    @GET("user/games/all")
    Call<ResponseBody> getAllGames();

    @GET("user/games")
    Call<ResponseBody> getUserGames();



    //Posts
    @GET("user/post")
    Call<ResponseBody> getUserPosts();

    @Multipart
    @POST("user/post")
    Call<ResponseBody> addUserPost(@Part MultipartBody.Part file);

    @DELETE("user/post")
    Call<ResponseBody> deleteUserPost(@Query("id") int Id);

    @FormUrlEncoded
    @POST("user/post/add-view")
    Call<ResponseBody> addPostViewCount(@Field("id") int Id);




}
