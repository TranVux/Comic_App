package com.example.assignment.models;

import java.io.Serializable;

public class Category implements Serializable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

    public Category() {
    }

    private String id, title, thumbnail, displayColor;

    public Category(String id, String title, String thumbnail, String displayColor) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.displayColor = displayColor;
    }
}
