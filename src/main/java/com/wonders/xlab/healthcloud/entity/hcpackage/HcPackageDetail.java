package com.wonders.xlab.healthcloud.entity.hcpackage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mars on 15/7/3.
 * 健康包详细
 */
@Entity
@Table(name = "hc_package_detail")
public class HcPackageDetail extends AbstractBaseEntity<Long> {

    /** 健康包 */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private HcPackage hcPackage;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务配图
     */
    private String icon;

    /** 任务详细信息 */
    @Column(columnDefinition = "TEXT")
    private String detail;

    /**
     * 点击次数
     */
    private int clickAmount;

    /**
     * 是否需要补充内容
     */
    private boolean isNeedSupplemented;

    /**
     * 补充内容
     */
    private String supplemented;

    /**
     * 任务推荐开始时间
     */
    @Temporal(TemporalType.TIME)
    private Date recommendTimeFrom;

    /**
     * 任务推荐结束时间
     */
    @Temporal(TemporalType.TIME)
    private Date recommendTimeTo;
    /**
     * 任务积分
     */
    private int integration;

    /**
     * 是否全天
     */
    private boolean isFullDay;

    private Integer taskDay;

    public HcPackageDetail() {
    }

    public HcPackageDetail(HcPackage hcPackage, String detail) {
        this.hcPackage = hcPackage;
        this.detail = detail;
    }

    public HcPackageDetail(HcPackage hcPackage, String taskName, String detail, boolean isNeedSupplemented, String supplemented, int integration, boolean isFullDay) {
        this.hcPackage = hcPackage;
        this.taskName = taskName;
        this.detail = detail;
        this.isNeedSupplemented = isNeedSupplemented;
        this.supplemented = supplemented;
        this.integration = integration;
        this.isFullDay = isFullDay;
    }

    public HcPackage getHcPackage() {
        return hcPackage;
    }

    public void setHcPackage(HcPackage hcPackage) {
        this.hcPackage = hcPackage;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getClickAmount() {
        return clickAmount;
    }

    public void setClickAmount(int clickAmount) {
        this.clickAmount = clickAmount;
    }

    public boolean isNeedSupplemented() {
        return isNeedSupplemented;
    }

    public void setIsNeedSupplemented(boolean isNeedSupplemented) {
        this.isNeedSupplemented = isNeedSupplemented;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public String getSupplemented() {
        return supplemented;
    }

    public void setSupplemented(String supplemented) {
        this.supplemented = supplemented;
    }

    public Date getRecommendTimeTo() {
        return recommendTimeTo;
    }

    public void setRecommendTimeTo(Date recommendTimeTo) {
        this.recommendTimeTo = recommendTimeTo;
    }

    public Date getRecommendTimeFrom() {
        return recommendTimeFrom;
    }

    public void setRecommendTimeFrom(Date recommendTimeFrom) {
        this.recommendTimeFrom = recommendTimeFrom;
    }

    public boolean isFullDay() {
        return isFullDay;
    }

    public void setIsFullDay(boolean isFullDay) {
        this.isFullDay = isFullDay;
    }

    public Integer getTaskDay() {
        return taskDay;
    }

    public void setTaskDay(Integer taskDay) {
        this.taskDay = taskDay;
    }
}
