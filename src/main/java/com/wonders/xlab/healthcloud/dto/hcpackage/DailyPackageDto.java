package com.wonders.xlab.healthcloud.dto.hcpackage;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by mars on 15/7/8.
 */
public class DailyPackageDto {

    /** 健康计划id */
    private Long packageDetailId;

    /** 开始时间 */
    private Date recommendTimeFrom;

    /** 任务名称 */
    private String taskName;

    /** 是否完成 */
    @JsonIgnore
    private boolean complete;

    private int isCompleted;

    public DailyPackageDto() {
    }

    public DailyPackageDto(Long packageDetailId, Date recommendTimeFrom, String taskName, boolean complete) {
        this.packageDetailId = packageDetailId;
        this.recommendTimeFrom = recommendTimeFrom;
        this.taskName = taskName;
        this.complete = complete;
    }

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

    public int getIsCompleted() {
        if (complete) return 1;
        else return 0;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

}
