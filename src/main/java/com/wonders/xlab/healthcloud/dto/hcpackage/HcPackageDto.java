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

    /**
     * 任务包详细介绍
     */
    @NotBlank(message = "任务包详细介绍不能为空")
    private String detailDescription;

    /**
     * 持续时间－－以天为单位
     */
    @NotNull(message = "持续时间不能为空")
    private int duration;

    /**
     * 图片地址
     */
    @NotBlank(message = "图片地址不能为空")
    private String iconUrl;

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
    @NotNull(message = "推荐值不能为空")
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
    private String loops;


    /**
     * 补充内容
     */
    @NotBlank(message = "补充内容不能为空")
    private String supplemented;

//    @NotNull(message = "三级分类不能为空")
    private Long  healthCategoryId;

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

    public Long getHealthCategoryId() {
        return healthCategoryId;
    }

    public void setHealthCategoryId(Long healthCategoryId) {
        this.healthCategoryId = healthCategoryId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDetailDescriptionIcon() {
        return detailDescriptionIcon;
    }

    public void setDetailDescriptionIcon(String detailDescriptionIcon) {
        this.detailDescriptionIcon = detailDescriptionIcon;
    }

    public String getLoops() {
        return loops;
    }

    public void setLoops(String loops) {
        this.loops = loops;
    }
}
