package com.wonders.xlab.healthcloud.entity.banner;

/**
 * Created by mars on 15/7/6.
 */
public enum BannerTag {

    /**
     * 计划
     */
    Plan("计划"),

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
    Inquiry("问诊"),

    /**
     * 商城
     */
    Store("商城"),

    /**
     * 个人中心
     */
    PersonCenter("个人中心"),

    /**
     * 购药
     */
    ShoppingDrug("购药"),

    /**
     * 活动
     */
    Activity("活动");



    private String name;

    BannerTag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
