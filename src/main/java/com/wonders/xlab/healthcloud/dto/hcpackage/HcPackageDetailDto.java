package com.wonders.xlab.healthcloud.dto.hcpackage;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/4.
 */
public class HcPackageDetailDto {


    /** 健康包 */
    @NotNull(message = "健康包id不能为空")
    private String hcPackageId;

    /**
     * 任务名称
     */
    @NotNull(message = "任务名称不能为空")
    private String taskName;

//    /**
//     * 任务配图
//     */
//    private String icon;

    /** 任务详细信息 */
    @NotNull(message = "任务详细介绍不能为空")
    private String detail;

    /**
     * 是否需要补充内容
     */
    private Boolean isNeedSupplemented;

    /**
     * 补充内容
     */
    @NotNull(message = "补充内容不能为空")
    private String supplemented;

    /**
     * 任务推荐时间
     */
    @NotNull(message = "任务推荐开始时间不能为空")
    private String recommendTimeFrom;

    @NotNull(message = "任务推荐结束时间不能为空")
    private String recommendTimeTo;
    /**
     * 任务积分
     */
    private Integer integration;

    public String getHcPackageId() {
        return hcPackageId;
    }

    public void setHcPackageId(String hcPackageId) {
        this.hcPackageId = hcPackageId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getRecommendTimeFrom() {
        return recommendTimeFrom;
    }

    public void setRecommendTimeFrom(String recommendTimeFrom) {
        this.recommendTimeFrom = recommendTimeFrom;
    }

    public String getRecommendTimeTo() {
        return recommendTimeTo;
    }

    public void setRecommendTimeTo(String recommendTimeTo) {
        this.recommendTimeTo = recommendTimeTo;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }
}
