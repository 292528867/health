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

    /** 任务名称 */
    private String taskName;

    /** 是否完成 0 未完成 1 完成 */
    private int isCompleted;

    /** 点击次数 */
    private int clickAmount;

    /** 是显示当前的数据 0 否 1 是 */
    private int current;

    public DailyPackageDto() {
    }

    public DailyPackageDto(Long packageDetailId, Date recommendTimeFrom, String taskName) {
        this.packageDetailId = packageDetailId;
        this.recommendTimeFrom = recommendTimeFrom;
        this.taskName = taskName;
    }

    public DailyPackageDto(Long packageDetailId, Date recommendTimeFrom, String taskName, int clickAmount) {
        this.clickAmount = clickAmount;
        this.packageDetailId = packageDetailId;
        this.recommendTimeFrom = recommendTimeFrom;
        this.taskName = taskName;
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

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getClickAmount() {
        return clickAmount;
    }

    public void setClickAmount(int clickAmount) {
        this.clickAmount = clickAmount;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
