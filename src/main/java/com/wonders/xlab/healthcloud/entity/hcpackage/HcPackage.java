package com.wonders.xlab.healthcloud.entity.hcpackage;

/**
 * Created by mars on 15/7/3.
 * 健康包
 */
import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hc_package")
public class HcPackage extends AbstractBaseEntity<Long> {

    /** 标题 */
    private String title;

    /** 描述 */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** 图片地址 */
    private String iconUrl;

    /** 是否推荐 */
    private boolean recommend;

    /** 推荐值 */
    private int recommendValue;

    public HcPackage() {
    }

    public HcPackage(String title, String description, String iconUrl, boolean recommend, int recommendValue) {
        this.title = title;
        this.description = description;
        this.iconUrl = iconUrl;
        this.recommend = recommend;
        this.recommendValue = recommendValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getRecommendValue() {
        return recommendValue;
    }

    public void setRecommendValue(int recommendValue) {
        this.recommendValue = recommendValue;
    }
}
