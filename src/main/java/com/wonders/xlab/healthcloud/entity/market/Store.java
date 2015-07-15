package com.wonders.xlab.healthcloud.entity.market;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/11.
 */
@Entity
@Table(name = "hc_store")
public class Store extends AbstractBaseEntity<Long> {

    /** 名称 */
    private String name;

    /** 价格 */
    private double price;

    /** 医药地址 */
    private String medicineUrl;

    /** 图片 */
    private String picUrl;

    /** 标签 */
    @Enumerated
    private Tag tag;

    /** 简述 */
    private String description;

    /** 排序顺序 升序 */
    private int position;

    public enum Tag {
        New, Hot
    }

    public Store() {
    }

    public Store(String name, double price, String medicineUrl, String picUrl, String description) {
        this.name = name;
        this.price = price;
        this.medicineUrl = medicineUrl;
        this.picUrl = picUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMedicineUrl() {
        return medicineUrl;
    }

    public void setMedicineUrl(String medicineUrl) {
        this.medicineUrl = medicineUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
