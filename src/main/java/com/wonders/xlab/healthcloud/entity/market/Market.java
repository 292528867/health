package com.wonders.xlab.healthcloud.entity.market;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/11.
 */
@Entity
@Table(name = "hc_market\")
public class Market extends AbstractBaseEntity<Long> {

    /** 名称 */
    private String name;

    /** 价格 */
    private double price;

    /** 医药地址 */
    private String medicineUrl;

    /** 图片 */
    private String iconUrl;

    public Market() {
    }

    public Market(String name, double price, String medicineUrl, String iconUrl) {
        this.name = name;
        this.price = price;
        this.medicineUrl = medicineUrl;
        this.iconUrl = iconUrl;
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
}
