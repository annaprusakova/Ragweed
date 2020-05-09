package com.prusakova.ragweed.api;

import com.prusakova.ragweed.model.Article;
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

//    @GET("getarticles.php")
//    Call<List<Article>> getArticle();
    @GET("articles.php")
    Call<List<Article>> getArticle(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

//    @POST("getarticles.php")
//    Call<Article> article(@Field("article_id"));


}
