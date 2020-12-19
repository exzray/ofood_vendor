package com.exzray.ofoodvendor.model;

import java.util.Date;

public class ModelProduct {

    private String name = "";
    private String description = "";
    private String image_photo = "";
    private String category_uid = "";

    private Date created = new Date();
    private Date updated = new Date();

    private Integer position = 0;

    private Double price = 0.0;

    private Boolean enable = true;


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

    public String getCategory_uid() {
        return category_uid;
    }

    public void setCategory_uid(String category_uid) {
        this.category_uid = category_uid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
