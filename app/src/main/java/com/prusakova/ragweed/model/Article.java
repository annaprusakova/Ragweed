package com.prusakova.ragweed.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("article_id")
    private int article_id;
    @SerializedName("article_name")
    private String article_name;
    @SerializedName("article_link")
    private String article_link;


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

}
