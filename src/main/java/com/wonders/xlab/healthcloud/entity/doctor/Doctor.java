package com.wonders.xlab.healthcloud.entity.doctor;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/2.
 */
@Entity
@Table(name = "hc_doctor")
public class Doctor extends AbstractBaseEntity<Long> {
    
    /** 手机 */
    private String phone;

    /** 头像 */
    private String avatar;

    /** 用户名 */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
