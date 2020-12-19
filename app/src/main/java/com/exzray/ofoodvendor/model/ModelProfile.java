package com.exzray.ofoodvendor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelProfile {

    private List<String> contact = new ArrayList<>();

    private String name = "";
    private String address = "";
    private String image_photo = "";

    private Date joined = new Date();
    private Date edited = new Date();
    private Date signed = new Date();

    public List<String> getContact() {
        return contact;
    }

    public void setContact(List<String> contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_photo() {
        return image_photo;
    }

    public void setImage_photo(String image_photo) {
        this.image_photo = image_photo;
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

    public Date getSigned() {
        return signed;
    }

    public void setSigned(Date signed) {
        this.signed = signed;
    }
}
