package com.wonders.xlab.healthcloud.dto.hcpackage;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/4.
 */
public class HcPackageDto {

    /**
     * 任务包名称
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 任务包简介
     */
    @NotBlank(message = "任务包简介不能为空")
    private String description;

    @NotBlank(message = "副标题不能为空")
    private String subtitle;

    /**
     * 任务包详细介绍
     */
    @NotBlank(message = "任务包详细介绍不能为空")
    private String detailDescription;

    /**
     * 持续时间－－以天为单位
     */
    private int duration;

    /**
     * 图片地址
     */
    @NotBlank(message = "图片地址不能为空")
    private String icon;

    /**
     * 详细介绍界面图片
     */
    @NotBlank(message = "详细介绍配图地址不能为空")
    private String detailDescriptionIcon;

    /**
     * 是否推荐
     */
    @NotNull(message = "是否推荐不能为空")
    private Boolean recommend;

    /**
     * 推荐值
     */
    private int recommendValue;

    /**
     * 是否需要补充内容
     */
    @NotNull(message = "是否需要不能为空")
    private Boolean isNeedSupplemented;


    /**
     * 是否循环
     */
    @NotBlank
    private boolean loops;

    /** 首页小图 */
    private String smallIcon;

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

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
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

    public boolean getLoops() {
        return loops;
    }

    public void setLoops(boolean loops) {
        this.loops = loops;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }
}
