package com.wonders.xlab.healthcloud.entity.steward;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
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
     * 推荐包包含服务
     */
    @OneToMany
    @JoinTable(
            name = "HC_PACKAGE_SERVICES_RALATION"
    )
    private Set<StewardService> stewardServices;

    public Steward.Rank getRank() {
        return rank;
    }

    public void setRank(Steward.Rank rank) {
        this.rank = rank;
    }

    public Set<StewardService> getStewardServices() {
        return stewardServices;
    }

    public void setStewardServices(Set<StewardService> stewardServices) {
        this.stewardServices = stewardServices;
    }
}
