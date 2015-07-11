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

    /** 资质 */
    private String recordUrl;

    /**
     * 用户使用平台
     */
    @Enumerated
    private AppPlatform appPlatform;

    public enum AppPlatform {
        Android,Ios,Other
    }

    /**
     * 真实姓名
     */
    private String iCardName;

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public Doctor() {
        super();
    }

    public Doctor(String tel, String iconUrl, Sex sex, double height, double weight, int age, Date birthday, Date createdDate, Date lastModifiedDate, String recordUrl) {
        super(tel, iconUrl, sex, height, weight, age, birthday, createdDate, lastModifiedDate);
        this.recordUrl = recordUrl;
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
}
