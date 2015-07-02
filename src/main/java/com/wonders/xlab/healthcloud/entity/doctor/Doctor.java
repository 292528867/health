package com.wonders.xlab.healthcloud.entity.doctor;

import com.wonders.xlab.healthcloud.entity.BaseInfo;

import javax.persistence.Entity;
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

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public Doctor(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public Doctor(String tel, String nickName, String iconUrl, Sex sex, double height, double weight, int age, Date birthday, Date createdDate, Date lastModifiedDate, String recordUrl) {
        super(tel, nickName, iconUrl, sex, height, weight, age, birthday, createdDate, lastModifiedDate);
        this.recordUrl = recordUrl;
    }
}
