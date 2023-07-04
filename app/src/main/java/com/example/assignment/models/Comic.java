package com.example.assignment.models;

import java.io.Serializable;

public class Comic implements Serializable {
    private String id, title, subTitle, country, synopsis, thumbnail, categories;
    private Author author;
    private User publishBy;

    public Comic(String id, String title, String subTitle, String country, String synopsis, String thumbnail, String categories, Author author, User publishBy) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.country = country;
        this.synopsis = synopsis;
        this.thumbnail = thumbnail;
        this.categories = categories;
        this.author = author;
        this.publishBy = publishBy;
    }

    public Comic() {
    }

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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public User getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(User publishBy) {
        this.publishBy = publishBy;
    }
}
