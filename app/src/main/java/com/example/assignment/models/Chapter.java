package com.example.assignment.models;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String id, title, content;
    private int index;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Chapter() {
    }

    public Chapter(String id, String title, String content, int index) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.index = index;
    }
}
