package com.prusakova.ragweed;

import com.prusakova.ragweed.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @POST("register.php")
    @FormUrlEncoded
    Call<User> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @POST("login.php")
    @FormUrlEncoded
    Call<User> login(@Field("name") String name, @Field("password") String password);
}
