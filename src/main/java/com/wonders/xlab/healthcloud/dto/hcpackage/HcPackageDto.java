package com.wonders.xlab.healthcloud.dto.hcpackage;

import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/4.
 */
public class HcPackageDto {

    /**
     * 任务包名称
     */
    @NotNull(message = "标题不能为空")
    private String title;

    /**
     * 任务包简介
     */
    @NotNull(message = "任务包简介不能为空")
    private String description;

    /**
     * 任务包详细介绍
     */
    @NotNull(message = "任务包详细介绍不能为空")
    private String detailDescription;

    /**
     * 持续时间－－以天为单位
     */
    @NotNull(message = "持续时间不能为空")
    private int duration;

//    /**
//     * 图片地址
//     */
//    @NotNull(message = "图片地址不能为空")
//    private String iconUrl;
//
//    /**
//     * 详细介绍界面图片
//     */
//    @NotNull(message = "详细介绍配图地址不能为空")
//    private String detailDescriptionIcon;

    /**
     * 是否推荐
     */
    @NotNull(message = "是否推荐不能为空")
    private Boolean recommend;

    /**
     * 推荐值
     */
    @NotNull(message = "推荐值不能为空")
    private int recommendValue;

    /**
     * 是否需要补充内容
     */
    @NotNull(message = "是否需要不能为空")
    private Boolean isNeedSupplemented;


    /**
     * 补充内容
     */
    @NotNull(message = "补充内容不能为空")
    private String supplemented;

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
    public int getRecommendValue() {
        return recommendValue;
    }

    public void setRecommendValue(int recommendValue) {
        this.recommendValue = recommendValue;
    }

    public Boolean getIsNeedSupplemented() {
        return isNeedSupplemented;
    }

    public void setIsNeedSupplemented(Boolean isNeedSupplemented) {
        this.isNeedSupplemented = isNeedSupplemented;
    }

    public String getSupplemented() {
        return supplemented;
    }

    public void setSupplemented(String supplemented) {
        this.supplemented = supplemented;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
}
