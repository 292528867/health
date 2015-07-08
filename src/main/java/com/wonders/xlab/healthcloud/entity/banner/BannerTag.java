package com.wonders.xlab.healthcloud.entity.banner;

/**
 * Created by mars on 15/7/6.
 */
public enum BannerTag {

    /**
     * 日程
     */
    Schedule("日程"),

    /**
     * 发现
     */
    Discovery("发现"),

    /**
     * 管家
     */
    Steward("管家"),

    /**
     * 问诊
     */
    Inquiry("问诊");

    private String name;

    BannerTag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
