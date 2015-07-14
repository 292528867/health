package com.wonders.xlab.healthcloud.entity.doctor;

import com.wonders.xlab.healthcloud.entity.BaseInfo;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by mars on 15/7/2.
 */
@Entity
@Table(name = "hc_doctor")
public class Doctor extends BaseInfo<Long> {

    /**
     * 身份证
     */
    private String iCardUrl;

    /**
     * 用户使用平台
     */
    @Enumerated
    private AppPlatform appPlatform;

    /**
     * 真实姓名
     */
    private String iCardName;

    /**
     * 职称证地址
     */
    private String qualificationUrl;

    /**
     * 职称名
     */
    private String qualificationName;

    /**
     * 执行认证
     */
    private String permitUrl;

    @Enumerated
    private Checked checked = Checked.undo;

    /**
     * 审核 ：未审核、未通过、已通过
     */
    public enum Checked {
        undo, apply, fail, passed
    }

    /**
     * 医院
     */
    private String hospital;

    /**
     * 科室
     */
    private String department;

    @Enumerated
    private Valid valid = Valid.invalid;

    private int integral;

    /**
     * invalid 用户无效（未完善用户信息），valid 用户有效
     */
    public enum Valid {
        invalid, valid
    }

    public Doctor() {
        super();
    }

    public AppPlatform getAppPlatform() {
        return appPlatform;
    }

    public void setAppPlatform(AppPlatform appPlatform) {
        this.appPlatform = appPlatform;
    }

    public String getiCardName() {
        return iCardName;
    }

    public void setiCardName(String iCardName) {
        this.iCardName = iCardName;
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

    public int getChecked() {
        return checked.ordinal();
    }

    public void setChecked(Checked checked) {
        this.checked = checked;
    }

    public int getValid() {
        return valid.ordinal();
    }

    public void setValid(Valid valid) {
        this.valid = valid;
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

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getiCardUrl() {
        return iCardUrl;
    }

    public void setiCardUrl(String iCardUrl) {
        this.iCardUrl = iCardUrl;
    }
}
