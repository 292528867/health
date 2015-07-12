package com.wonders.xlab.healthcloud.dto.doctor;

/**
 * Created by Jeffrey on 15/7/12.
 */
public class DoctorQualificationUrlDto {
    /**
     * 头像地址
     */
    private String iconUrl;

    /**
     * 身份证
     */
    private String iCardUrl;
    /**
     * 职称证
     */
    private String qualificationUrl;

    /**
     * 执行认证
     */
    private String permitUrl;


    public DoctorQualificationUrlDto() {
    }

    public DoctorQualificationUrlDto(String iconUrl, String iCardUrl, String qualificationUrl, String permitUrl) {
        this.iconUrl = iconUrl;
        this.iCardUrl = iCardUrl;
        this.qualificationUrl = qualificationUrl;
        this.permitUrl = permitUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getiCardUrl() {
        return iCardUrl;
    }

    public void setiCardUrl(String iCardUrl) {
        this.iCardUrl = iCardUrl;
    }

    public String getQualificationUrl() {
        return qualificationUrl;
    }

    public void setQualificationUrl(String qualificationUrl) {
        this.qualificationUrl = qualificationUrl;
    }

    public String getPermitUrl() {
        return permitUrl;
    }

    public void setPermitUrl(String permitUrl) {
        this.permitUrl = permitUrl;
    }
}
