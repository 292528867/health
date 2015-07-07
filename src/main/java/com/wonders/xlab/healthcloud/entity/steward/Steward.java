package com.wonders.xlab.healthcloud.entity.steward;

import com.wonders.xlab.healthcloud.entity.BaseInfo;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Entity
@Table(name = "HC_STEWARD")
public class Steward extends BaseInfo<Long> {

    /**
     * 管家等级
     */
    @Enumerated
    private Rank rank;

    public enum Rank {
        S, A, B, C
    }

    /**
     *  管家分数
     */
    private int stewardIntegration;

    /**
     * 管家服务天数
     */
    private int servicedPeriod;

    /**
     * 执照
     */
    private String License;

    /**
     * 位置
     */
    @Enumerated
    private Location location;

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public int getStewardIntegration() {
        return stewardIntegration;
    }

    public void setStewardIntegration(int stewardIntegration) {
        this.stewardIntegration = stewardIntegration;
    }

    public int getServicedPeriod() {
        return servicedPeriod;
    }

    public void setServicedPeriod(int servicedPeriod) {
        this.servicedPeriod = servicedPeriod;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
