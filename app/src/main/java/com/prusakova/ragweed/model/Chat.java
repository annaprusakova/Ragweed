package com.prusakova.ragweed.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chat implements Serializable {
    @SerializedName("chat_id")
   private int chat_id;
    @SerializedName("chat_name")
   private  String chat_name;
    @SerializedName("lastMessage")
   private String lastMessage;
    @SerializedName("chat_created_at")
   private String timestamp;
    @SerializedName("unreadCount")
   private int unreadCount;

    public Chat() {
    }

    public Chat(int chat_id, String chat_name, String lastMessage, String timestamp, int unreadCount) {
        this.chat_id = chat_id;
        this.chat_name = chat_name;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.unreadCount = unreadCount;
    }

    public int getChatId() {
        return chat_id;
    }

    public void setChatId(int chat_id) {
        this.chat_id = chat_id;
    }

    public String getChatName() {
        return chat_name;
    }

    public void setChatName(String chat_name) {
        this.chat_name = chat_name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
