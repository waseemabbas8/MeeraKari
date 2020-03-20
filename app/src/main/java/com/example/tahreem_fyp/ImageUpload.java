package com.example.tahreem_fyp;

public class ImageUpload {

    public String name;
    public String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageUpload(String url) {
        this.url = url;
    }
}
