package com.wonders.xlab.healthcloud.dto.doctor;

import com.wonders.xlab.healthcloud.entity.doctor.Doctor;

/**
 * Created by Jeffrey on 15/7/11.
 */
public class DoctorBaseInfoDto {
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

    /**
     * 真实姓名
     */
    private String iCardName;

    /**
     * 职称
     */
    private String qualificationName;

    public DoctorBaseInfoDto() {
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
