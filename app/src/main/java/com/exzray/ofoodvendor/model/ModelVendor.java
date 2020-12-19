package com.exzray.ofoodvendor.model;

import java.util.Date;

public class ModelVendor {

    private String name = "";
    private String description = "";
    private String image_photo = "";
    private String image_banner = "";

    private Date joined = new Date();
    private Date edited = new Date();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_photo() {
        return image_photo;
    }

    public void setImage_photo(String image_photo) {
        this.image_photo = image_photo;
    }

    public String getImage_banner() {
        return image_banner;
    }

    public void setImage_banner(String image_banner) {
        this.image_banner = image_banner;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }
}
