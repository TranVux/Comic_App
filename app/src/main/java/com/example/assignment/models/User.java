package com.example.assignment.models;

import java.io.Serializable;

public class User implements Serializable {
    private String id, user_name, email, uid, avatar;

    public User(String id, String user_name, String email, String uid, String avatar) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.uid = uid;
        this.avatar = avatar;
    }

    public User(String id, String user_name, String email, String avatar) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.avatar = avatar;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
