package com.wonders.xlab.healthcloud.entity.hcpackage;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 二大类分类
 * Created by Jeffrey on 15/7/9.
 */

@Entity
@Table(name = "HC_CLASSIFICATION")
public class Classification extends AbstractBaseEntity<Long> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String title;

    private String icon;

    /**
     * 是否推荐
     */
    private boolean recommend;

    /**
     * 推荐值
     */
    private int recommendValue;

    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public int getRecommendValue() {
        return recommendValue;
    }

    public void setRecommendValue(int recommendValue) {
        this.recommendValue = recommendValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
