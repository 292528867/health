package com.wonders.xlab.healthcloud.entity.banner;

/**
 * Created by mars on 15/7/6.
 */
public enum BannerType {

    Top("上"),

    Bottom("下");

    private String name;

    BannerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
