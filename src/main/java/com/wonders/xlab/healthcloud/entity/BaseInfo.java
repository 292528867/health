package com.wonders.xlab.healthcloud.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by mars on 15/7/2.
 */
@MappedSuperclass
public abstract class BaseInfo<ID extends Serializable> extends AbstractPersistable<ID> {

    /**
     * 手机
     */
    @Column(unique = true, length = 11)
    protected String tel = "";

    public enum AppPlatform {
        Android, Ios, Other
    }

    /**
     * 头像地址
     */
    protected String iconUrl = "";

    /**
     * 昵称
     */
    private String nickName = "";

    /**
     * 性别
     */
    @Enumerated
    protected Sex sex = Sex.Unkown;

    public enum Sex {
        Male, Female, Unkown
    }

    @Temporal(DATE)
    protected Date birthday;

    @CreatedDate
    @Temporal(TIMESTAMP)
    protected Date createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastModifiedDate;

    public BaseInfo() {
        super();
    }

    public BaseInfo(String tel, String iconUrl, String nickName, Sex sex, Date birthday, Date createdDate, Date lastModifiedDate) {
        this.tel = tel;
        this.iconUrl = iconUrl;
        this.nickName = nickName;
        this.sex = sex;
        this.birthday = birthday;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
