package com.wonders.xlab.healthcloud.entity.evaluation;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 测评类型
 * Created by mars on 15/7/5.
 */
@Entity
@Table(name = "hc_evaluation_type")
public class EvaluationType extends AbstractBaseEntity<Long> {

    /** 类型名称 */
    private String name;

    public EvaluationType() {
    }

    public EvaluationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
