package com.wonders.xlab.healthcloud.dto.hcpackage;

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

    /** 详细 */
    private String detail;

    /** 是否需要补充内容的显示 */
    private int type;

    /** 是否完成 0 否 1 是 */
    private int complete;

    private Set<UserStatementDto> statementDtos;

    /** 图片，视频地址 以后缀区分*/
    private String icon;

    /** 图片类型 0 没有图片 1 图片 2 视频 */
    private int iconType;

    public DayPackageDetailDto() {
    }

    public DayPackageDetailDto(Long detailId, String title, int clickAmount, String detail, String icon) {
        this.detailId = detailId;
        this.title = title;
        this.clickAmount = clickAmount;
        this.detail = detail;
        this.icon = icon;
    }

    public DayPackageDetailDto(Long detailId, String title, int clickAmount, int type, String detail, int complete) {
        this.detailId = detailId;
        this.title = title;
        this.clickAmount = clickAmount;
        this.type = type;
        this.detail = detail;
        this.complete = complete;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public Set<UserStatementDto> getStatementDtos() {
        return statementDtos;
    }

    public void setStatementDtos(Set<UserStatementDto> statementDtos) {
        this.statementDtos = statementDtos;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconType() {
        return iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }
}
