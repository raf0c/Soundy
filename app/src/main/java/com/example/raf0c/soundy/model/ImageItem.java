package com.example.raf0c.soundy.model;

/**
 * Created by raf0c on 15/09/15.
 */
public class ImageItem  {

    private String url;
    private String title;

    public ImageItem(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}