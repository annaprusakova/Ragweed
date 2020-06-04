package com.prusakova.ragweed.model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("comment_id")
    private int comment_id;
    @SerializedName("comment_date")
    private String comment_date;
    @SerializedName("comment_text")
    private String comment_text;
    @SerializedName("comment_user_id")
    private int comment_user_id;
    private String name;
    private String user_photo;
    @SerializedName("comment_topic_id")
    private int comment_topic_id;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public int getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(int comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public int getComment_topic_id() {
        return comment_topic_id;
    }

    public void setComment_topic_id(int comment_topic_id) {
        this.comment_topic_id = comment_topic_id;
    }

    public String get_Name() {
        return name;
    }

    public void setUser_name(String user_name) {
        this.name = user_name;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

}
