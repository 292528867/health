package com.wonders.xlab.healthcloud.dto.hcpackage;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;

import javax.persistence.*;

/**
 * Created by mars on 15/7/8.
 */
public class UserPackageOrderDto  {

    private long id;

    /**
     * 任务包名称
     */
    private String title;

    /**
     * 任务包简介
     */
    private String description;

    /**
     * 任务包详细介绍
     */
    private String detailDescription;

    /**
     * 持续时间－－以天为单位
     */
    private int duration;

    /**
     * 图片地址
     */
    private String icon;

    /**
     * 详细介绍界面图片
     */
    private String detailDescriptionIcon;

    /**
     * 是否推荐
     */
    private boolean recommend;

    /**
     * 推荐值
     */
    private int recommendValue;

    /**
     * 点击次数
     */
    private int clickAmount;

    /**
     * 加入次数
     */
    private int joinAmount;

    /**
     * 是否需要补充内容
     */
    private boolean isNeedSupplemented;

    private Sex sex;

    public enum Sex {
        Male, Female, All
    }

    /**
     * 补充内容
     */
    private String supplemented;

    /**
     * 是否循环
     */
    private String loops;

    private HealthCategory healthCategory;

    /** 循环次数 */
    private int cycleLimit;

    private Boolean isJoin;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetailDescriptionIcon() {
        return detailDescriptionIcon;
    }

    public void setDetailDescriptionIcon(String detailDescriptionIcon) {
        this.detailDescriptionIcon = detailDescriptionIcon;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public int getRecommendValue() {
        return recommendValue;
    }

    public void setRecommendValue(int recommendValue) {
        this.recommendValue = recommendValue;
    }

    public int getClickAmount() {
        return clickAmount;
    }

    public void setClickAmount(int clickAmount) {
        this.clickAmount = clickAmount;
    }

    public int getJoinAmount() {
        return joinAmount;
    }

    public void setJoinAmount(int joinAmount) {
        this.joinAmount = joinAmount;
    }

    public boolean isNeedSupplemented() {
        return isNeedSupplemented;
    }

    public void setNeedSupplemented(boolean isNeedSupplemented) {
        this.isNeedSupplemented = isNeedSupplemented;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getSupplemented() {
        return supplemented;
    }

    public void setSupplemented(String supplemented) {
        this.supplemented = supplemented;
    }

    public String getLoops() {
        return loops;
    }

    public void setLoops(String loops) {
        this.loops = loops;
    }

    public HealthCategory getHealthCategory() {
        return healthCategory;
    }

    public void setHealthCategory(HealthCategory healthCategory) {
        this.healthCategory = healthCategory;
    }

    public int getCycleLimit() {
        return cycleLimit;
    }

    public void setCycleLimit(int cycleLimit) {
        this.cycleLimit = cycleLimit;
    }

    public Boolean getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Boolean isJoin) {
        this.isJoin = isJoin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
