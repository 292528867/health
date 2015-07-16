package com.wonders.xlab.healthcloud.dto.hcpackage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wonders.xlab.healthcloud.service.hcpackage.HcpackageService;

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

    /** 点击次数 */
    private int clickAmount;

    @JsonIgnore
    private int coefficient;

    @JsonIgnore
    private Date createdDate;


    public DailyPackageDto() {
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


    public int getClickAmount() {
        return HcpackageService.calculateClickCount(coefficient, clickAmount, createdDate);
    }

    public void setClickAmount(int clickAmount) {
        this.clickAmount = clickAmount;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
