package com.wonders.xlab.healthcloud.entity.lottery;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by qinwenshi on 7/13/15.
 */
@Entity
@Table(name = "hc_lottery_info")
public class LotteryInfo extends AbstractPersistable<Long> {

    private String name;

    private String description;

    private float probability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

}
