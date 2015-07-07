package com.wonders.xlab.healthcloud.entity.steward;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

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

//    /**
//     * 推荐包包含服务
//     */
//    @OneToMany
//    @JoinTable(
//            name = "HC_PACKAGE_SERVICES_RALATION"
//    )
//    private Set<Services> services;

    @Transient
    private Set<Services> services;

    @Transient
    private Steward steward;

    /**
     * 服务id，逗号分开
     */
    @JsonIgnore
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

    public Set<Services> getServices() {
        return services;
    }

    public void setServices(Set<Services> services) {
        this.services = services;
    }

    public Steward getSteward() {
        return steward;
    }

    public void setSteward(Steward steward) {
        this.steward = steward;
    }

    public String getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(String serviceIds) {
        this.serviceIds = serviceIds;
    }

    //    public Set<Services> getServices() {
//        return services;
//    }
//
//    public void setServices(Set<Services> services) {
//        this.services = services;
//    }
}
