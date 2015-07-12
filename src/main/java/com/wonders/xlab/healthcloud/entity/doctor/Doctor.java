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
     * 资质
     */
    private String recordUrl;

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

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public Doctor() {
        super();
    }

    public Doctor(double height, double weight, int age, String tel, String iconUrl, String nickName, Sex sex,
                  Date birthday, Date createdDate, Date lastModifiedDate, String recordUrl, AppPlatform appPlatform,
                  String iCardName, String qualificationUrl, String permitUrl, Checked checked, String hospital,
                  String department, Valid valid, int integral) {
        super(height, weight, age, tel, iconUrl, nickName, sex, birthday, createdDate, lastModifiedDate);
        this.recordUrl = recordUrl;
        this.appPlatform = appPlatform;
        this.iCardName = iCardName;
        this.qualificationUrl = qualificationUrl;
        this.permitUrl = permitUrl;
        this.checked = checked;
        this.hospital = hospital;
        this.department = department;
        this.valid = valid;
        this.integral = integral;
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

    public Checked getChecked() {
        return checked;
    }

    public void setChecked(Checked checked) {
        this.checked = checked;
    }

    public Valid getValid() {
        return valid;
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
}
