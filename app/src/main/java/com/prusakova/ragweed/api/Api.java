package com.prusakova.ragweed.api;



import com.prusakova.ragweed.model.Article;
import com.prusakova.ragweed.model.Comment;
import com.prusakova.ragweed.model.Location;
import com.prusakova.ragweed.model.Medicine;
import com.prusakova.ragweed.model.Tracker;
import com.prusakova.ragweed.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("register.php")
    @FormUrlEncoded
    Call<User> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @POST("login.php")
    @FormUrlEncoded
    Call<User> login(@Field("email") String email, @Field("password") String password);



    @GET("articles.php")
    Call<List<Article>> getArticle(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

    @GET("get_user_info.php")
    Call<List<User>> getUser(
            @Query("id")  int id
    );

    @FormUrlEncoded
    @POST("update_user.php")
    Call<User> updateUser(
            @Field("key") String key,
            @Field("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("user_gender") int user_gender,
            @Field("user_photo") String user_photo);

    @FormUrlEncoded
    @POST("update_password.php")
    Call<User> updatePassword(
            @Field("id") int id,
            @Field("password") String oldPassword,
            @Field("new_password") String newPassword);



    @POST("forgotpassword.php")
    @FormUrlEncoded
    Call<User> forgotpassword(@Field("email") String email);

    @GET("getmedicines.php")
    Call<List<Medicine>> getMed(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

    @FormUrlEncoded
    @POST("add_tracker.php")
    Call<Tracker> insertTracker(
            @Field("key") String key,
            @Field("tracker_date") String tracker_date,
            @Field("itchy_nose") int itchy_nose,
            @Field("water_eyes") int water_eyes,
            @Field("runny_nose") int runny_nose,
            @Field("user_id") int user_id,
            @Field("eye_redness") int eye_redness
            );


    @GET("get_tracker.php")
    Call<List<Tracker>> getTracker(
            @Query("key") int key
    );

    @GET("sort_med_by_cost.php")
    Call<List<Medicine>> getCost(
            @Query("item_type") String item_type
    );


    @FormUrlEncoded
    @POST("add_location.php")
    Call<Location> insertLocation(
            @Field("key") String key,
            @Field("loc_name") String loc_name,
            @Field("loc_date") String loc_date,
            @Field("loc_point") String loc_point,
            @Field("loc_description") String loc_description,
            @Field("user_id_loc") int user_id_loc,
            @Field("loc_latlng") String loc_latlng,
            @Field("loc_photo") String  loc_photo

    );

    @GET("get_location_by_user.php")
    Call<List<Location>> getLocationByUser(
            @Query("key") int keyword
    );

    @GET("get_location.php")
    Call<List<Location>> getLocation(
    );


    @GET("get_inf_loc_user.php")
    Call<List<User>> getUserLoc(
            @Query("key") int keyword
    );

    @GET("get_all_comments.php")
    Call<List<Comment>> getComments(
            @Query("table") String table,
            @Query("key") int keyword
    );


    @FormUrlEncoded
    @POST("add_comment.php")
    Call<Comment> addComment(
            @Field("key") String key,
            @Field("comment_date") String comment_date,
            @Field("comment_text") String comment_text,
            @Field("comment_user_id") int user_comment_id,
            @Field("comment_topic_id") int comment_topic_id);


}
