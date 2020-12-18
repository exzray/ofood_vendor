package com.exzray.ofoodvendor.model;

import com.exzray.ofoodvendor.R;

public class ModelNavigation {

    private String name = "";
    private String description = "";
    private String tag = "";

    private Integer icon = R.drawable.ic_outline_android_24;


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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
