package com.wonders.xlab.healthcloud.entity.hcpackage;

/**
 * Created by mars on 15/7/3.
 * 健康包
 */

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;

import javax.persistence.*;

@Entity
@Table(name = "hc_package")
public class HcPackage extends AbstractBaseEntity<Long> {

    /**
     * 任务包名称
     */
    private String title;

    /**
     * 任务包简介
     */
    @Column(length = 14)
    private String description;

    /**
     * 任务包详细介绍
     */
    @Column(columnDefinition = "TEXT")
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

    @Enumerated
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

    @ManyToOne(fetch = FetchType.LAZY)
    private HealthCategory healthCategory;

    /** 循环次数 */
    private int cycleLimit;

//    /**
//     * 用户健康包
//     */
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
//    @OrderBy(value = "id asc")
//    @JoinTable(name = "hc_user_package_relation", joinColumns = @JoinColumn(name = "package_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<User> users;

    public HcPackage() {
    }

    public HcPackage(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public HcPackage(String title, String description, String iconUrl, boolean recommend, int recommendValue) {
        this.title = title;
        this.description = description;
        this.icon = iconUrl;
        this.recommend = recommend;
        this.recommendValue = recommendValue;
    }

    public HcPackage(
            String title, String description, String detailDescription,
            int duration, String iconUrl, String detailDescriptionIcon,
            boolean recommend, int recommendValue, int clickAmount,
            int joinAmount, boolean isNeedSupplemented, HealthCategory healthCategory) {
        this.title = title;
        this.description = description;
        this.detailDescription = detailDescription;
        this.duration = duration;
        this.icon = iconUrl;
        this.detailDescriptionIcon = detailDescriptionIcon;
        this.recommend = recommend;
        this.recommendValue = recommendValue;
        this.clickAmount = clickAmount;
        this.joinAmount = joinAmount;
        this.isNeedSupplemented = isNeedSupplemented;
        this.healthCategory = healthCategory;
    }

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

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getRecommendValue() {
        return recommendValue;
    }

    public void setRecommendValue(int recommendValue) {
        this.recommendValue = recommendValue;
    }

    public HealthCategory getHealthCategory() {
        return healthCategory;
    }

    public void setHealthCategory(HealthCategory healthCategory) {
        this.healthCategory = healthCategory;
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

    public String getDetailDescriptionIcon() {
        return detailDescriptionIcon;
    }

    public void setDetailDescriptionIcon(String detailDescriptionIcon) {
        this.detailDescriptionIcon = detailDescriptionIcon;
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

    public void setIsNeedSupplemented(boolean isNeedSupplemented) {
        this.isNeedSupplemented = isNeedSupplemented;
    }

    public String getSupplemented() {
        return supplemented;
    }

    public void setSupplemented(String supplemented) {
        this.supplemented = supplemented;
    }

//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getCycleLimit() {
        return cycleLimit;
    }

    public void setCycleLimit(int cycleLimit) {
        this.cycleLimit = cycleLimit;
    }

    public String getLoops() {
        return loops;
    }

    public void setLoops(String loops) {
        this.loops = loops;
    }
}
