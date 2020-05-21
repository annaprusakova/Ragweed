package com.prusakova.ragweed.api;

import com.prusakova.ragweed.model.Article;
import com.prusakova.ragweed.model.Chat;
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



    @GET("get_chat_room.php")
    Call<List<Chat>> getChat(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );


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
            @Field("med_id") int med_id,
            @Field("itchy_nose") int itchy_nose,
            @Field("water_eyes") int water_eyes,
            @Field("runny_nose") int runny_nose
            );


    @GET("get_tracker.php")
    Call<List<Tracker>> getTracker(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );


}
