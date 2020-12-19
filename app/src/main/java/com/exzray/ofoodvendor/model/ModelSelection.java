package com.exzray.ofoodvendor.model;

import org.jetbrains.annotations.NotNull;

public class ModelSelection {

    private String tag = "";
    private String name = "";

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
