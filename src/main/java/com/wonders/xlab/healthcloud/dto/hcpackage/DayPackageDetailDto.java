package com.wonders.xlab.healthcloud.dto.hcpackage;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

/**
 * Created by mars on 15/7/8.
 */
public class DayPackageDetailDto {

    /** 计划id */
    private Long detailId;

    /** 标题 */
    private String title;

    /** 点击数 */
    private int clickAmount;

    /** 是否需要补充内容 */
    @JsonIgnore
    private boolean isNeedSupplemented;

    /** 详细 */
    private String detail;

    /** 是否需要补充内容的显示 */
    private int type;

    private Set<UserStatementDto> statementDtos;

    public DayPackageDetailDto() {
    }

    public DayPackageDetailDto(Long detailId, String title, int clickAmount, boolean isNeedSupplemented, String detail) {
        this.detailId = detailId;
        this.title = title;
        this.clickAmount = clickAmount;
        this.isNeedSupplemented = isNeedSupplemented;
        this.detail = detail;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getType() {
        if (isNeedSupplemented) return 1;
        else return 0;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Set<UserStatementDto> getStatementDtos() {
        return statementDtos;
    }

    public void setStatementDtos(Set<UserStatementDto> statementDtos) {
        this.statementDtos = statementDtos;
    }
}
