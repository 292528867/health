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

import static javax.persistence.TemporalType.*;

/**
 * Created by mars on 15/7/2.
 */
@MappedSuperclass
public abstract class BaseInfo<ID extends Serializable> extends AbstractPersistable<ID> {

    /**
     * 手机
     */
    @Column(unique = true, length = 11)
    private String tel = "";

    /**
     * 昵称
     */
    private String nickName = "";

    /**
     * 头像地址
     */
    private String iconUrl = "";

    /**
     * 性别
     */
    @Enumerated
    private Sex sex = Sex.Unkown;

    public enum Sex {
        Male, Female, Unkown
    }

    /**
     * 身高
     */
    private double height;

    /**
     * 体重
     */
    private double weight;

    /**
     * 年龄
     */
    private int age;

    @Temporal(DATE)
    private Date birthday;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModifiedDate;

    public BaseInfo() {
        super();
    }

    public BaseInfo(String tel, String nickName, String iconUrl, Sex sex, double height, double weight, int age, Date birthday, Date createdDate, Date lastModifiedDate) {
        this.tel = tel;
        this.nickName = nickName;
        this.iconUrl = iconUrl;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.age = age;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
}
