package com.wonders.xlab.healthcloud.entity.steward;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Entity
@Table(name = "HC_RECOMMEND_PACKAGE")
public class RecommendPackage extends AbstractPersistable<Long> {

    /**
     * 推荐包管家等级
     */
    private Steward.Rank rank;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 价格
     */
    private String price;

    /**
     * 服务id，逗号分开
     */
    private String serviceIds;

    public Steward.Rank getRank() {
        return rank;
    }

    public void setRank(Steward.Rank rank) {
        this.rank = rank;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(String serviceIds) {
        this.serviceIds = serviceIds;
    }
}
