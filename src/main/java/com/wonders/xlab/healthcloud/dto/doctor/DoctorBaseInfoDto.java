package com.wonders.xlab.healthcloud.dto.doctor;

import com.wonders.xlab.healthcloud.entity.doctor.Doctor;

/**
 * Created by Jeffrey on 15/7/11.
 */
public class DoctorBaseInfoDto {

    /**
     * 头像地址
     */
    private String iconUrl;

    /**
     * 性别
     */
    private Doctor.Sex sex;

    /**
     * 身高
     */
    private Double height;

    /**
     * 体重
     */
    private Double weight;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 医院
     */
    private String hospital;

    /**
     * 科室
     */
    private String department;

    private String iCardName;

    private String qualificationName;

    public DoctorBaseInfoDto() {
    }

    public DoctorBaseInfoDto(String iconUrl, Doctor.Sex sex, Double height, Double weight, Integer age, String hospital, String department, String iCardName) {
        this.iconUrl = iconUrl;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.hospital = hospital;
        this.department = department;
        this.iCardName = iCardName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Doctor.Sex getSex() {
        return sex;
    }

    public void setSex(Doctor.Sex sex) {
        this.sex = sex;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getiCardName() {
        return iCardName;
    }

    public void setiCardName(String iCardName) {
        this.iCardName = iCardName;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }
}
