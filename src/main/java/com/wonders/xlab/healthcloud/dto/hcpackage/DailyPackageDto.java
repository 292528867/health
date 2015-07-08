package com.wonders.xlab.healthcloud.dto.hcpackage;

import java.util.Date;

/**
 * Created by mars on 15/7/8.
 */
public class DailyPackageDto {

    /** 健康计划id */
    private Long packageDetailId;

    /** 开始时间 */
    private Date recommendTimeFrom;

    /** 结束时间 */
    private Date recommendTimeTo;

    /** 任务名称 */
    private String taskName;

    /** 是否完成 */
    private boolean complete;

    /** 过期 */
    private boolean outdated;

    public Long getPackageDetailId() {
        return packageDetailId;
    }

    public void setPackageDetailId(Long packageDetailId) {
        this.packageDetailId = packageDetailId;
    }

    public Date getRecommendTimeFrom() {
        return recommendTimeFrom;
    }

    public void setRecommendTimeFrom(Date recommendTimeFrom) {
        this.recommendTimeFrom = recommendTimeFrom;
    }

    public Date getRecommendTimeTo() {
        return recommendTimeTo;
    }

    public void setRecommendTimeTo(Date recommendTimeTo) {
        this.recommendTimeTo = recommendTimeTo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isOutdated() {
        return outdated;
    }

    public void setOutdated(boolean outdated) {
        this.outdated = outdated;
    }
}
