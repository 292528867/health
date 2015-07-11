package com.wonders.xlab.healthcloud.entity.market;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/11.
 */
@Entity
@Table(name = "hc_market")
public class Store extends AbstractBaseEntity<Long> {

    /** 名称 */
    private String name;

    /** 价格 */
    private double price;

    /** 医药地址 */
    private String medicineUrl;

    /** 图片 */
    private String iconUrl;

    /** 标签 */
    private Tag tag;

    /** 简述 */
    private String description;

    public enum Tag{
        New, Hot
    }

    public Store() {
    }

    public Store(String name, double price, String medicineUrl, String iconUrl, String description) {
        this.name = name;
        this.price = price;
        this.medicineUrl = medicineUrl;
        this.iconUrl = iconUrl;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
}
