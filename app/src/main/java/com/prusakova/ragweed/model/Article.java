package com.prusakova.ragweed.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("article_id")
    private int article_id;
    @SerializedName("article_name")
    private String article_name;
    @SerializedName("article_link")
    private String article_link;
    @SerializedName("article_img")
    private String article_img;
    @SerializedName("article_text")
    private String article_text;



    public int getArticleId() {
        return article_id;
    }

    public void setArticleId(int article_id) {
        this.article_id = article_id;
    }

    public String getArticleName() {
        return  article_name;
    }

    public void setArticleName(String  article_name) {
        this.article_name =  article_name;
    }

    public String getArticleLink() {
        return article_link;
    }

    public void setArtcleLink(String article_link) {
        this.article_link = article_link;
    }

    public String getArticle_img() {
        return article_img;
    }

    public void setArticle_img(String article_img) {
        this.article_img = article_img;
    }

    public String getArticle_text() {
        return article_text;
    }

    public void setArticle_text(String article_text) {
        this.article_text = article_text;
    }
}
