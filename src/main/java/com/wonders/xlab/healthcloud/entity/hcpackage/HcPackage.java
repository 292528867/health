package com.wonders.xlab.healthcloud.entity.hcpackage;

/**
 * Created by mars on 15/7/3.
 * 健康包
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.service.hcpackage.HcpackageService;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "hc_package")
public class HcPackage extends AbstractBaseEntity<Long> {

    /**
     * 任务包名称
     */
    private String title;

    /**
     *
     */
    private String subtitle;

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
    private boolean loops;

    @ManyToOne()
    private HealthCategory healthCategory;

    /** 循环次数 */
    private int cycleLimit;

    /** 首页小图 */
    private String smallIcon;

    @JsonIgnore
    private int coefficient;

    @OneToMany(mappedBy = "hcPackage",fetch = FetchType.LAZY)
    private Set<HcPackageDetail> hcPackageDetails;

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
            String title, String subtitle, String description, String detailDescription,
            int duration, String icon, String detailDescriptionIcon, boolean recommend,
            int recommendValue, int clickAmount, int joinAmount, boolean isNeedSupplemented,
            Sex sex, String supplemented, boolean loops, HealthCategory healthCategory,
            int cycleLimit, String smallIcon, int coefficient) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.detailDescription = detailDescription;
        this.duration = duration;
        this.icon = icon;
        this.detailDescriptionIcon = detailDescriptionIcon;
        this.recommend = recommend;
        this.recommendValue = recommendValue;
        this.clickAmount = clickAmount;
        this.joinAmount = joinAmount;
        this.isNeedSupplemented = isNeedSupplemented;
        this.sex = sex;
        this.supplemented = supplemented;
        this.loops = loops;
        this.healthCategory = healthCategory;
        this.cycleLimit = cycleLimit;
        this.smallIcon = smallIcon;
        this.coefficient = coefficient;
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
        return HcpackageService.calculateClickCount(clickAmount, getCreatedDate());
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

    public boolean isLoops() {
        return loops;
    }

    public void setLoops(boolean loops) {
        this.loops = loops;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Set<HcPackageDetail> getHcPackageDetails() {
        return hcPackageDetails;
    }

    public void setHcPackageDetails(Set<HcPackageDetail> hcPackageDetails) {
        this.hcPackageDetails = hcPackageDetails;
    }
}
