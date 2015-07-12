package com.wonders.xlab.healthcloud.dto.doctor;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Jeffrey on 15/7/12.
 */
public class DoctorQualificationDto {

    /**
     * 头像地址
     */
    private MultipartFile icon;

    /**
     * 身份证
     */
    private MultipartFile iCard;
    /**
     * 职称证
     */
    private MultipartFile qualification;

    /**
     * 执行认证
     */
    private MultipartFile permit;

    public DoctorQualificationDto() {
    }

    public MultipartFile getiCard() {
        return iCard;
    }

    public void setiCard(MultipartFile iCard) {
        this.iCard = iCard;
    }

    public MultipartFile getQualification() {
        return qualification;
    }

    public void setQualification(MultipartFile qualification) {
        this.qualification = qualification;
    }

    public MultipartFile getPermit() {
        return permit;
    }

    public void setPermit(MultipartFile permit) {
        this.permit = permit;
    }

    public MultipartFile getIcon() {
        return icon;
    }

    public void setIcon(MultipartFile icon) {
        this.icon = icon;
    }
}
