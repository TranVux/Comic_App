package com.example.assignment.models;

import java.io.Serializable;

public class Author implements Serializable {
    private String id, name, info;

    public Author(String id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }

    public Author() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
