package com.wonders.xlab.healthcloud.entity.banner;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/6.
 */
@Entity
@Table(name = "hc_banner")
public class Banner extends AbstractBaseEntity<Long> {

    /** Banner标签 0 计划 1 发现 2 管家 3 问诊 4 商城 5 个人中心 6 购药 */
    @Enumerated
    private BannerTag bannerTag;

    /** Banner位置 0 上 1 下 */
    @Enumerated
    @Column(nullable = false)
    private BannerType bannerType;

    /** 文章ID */
    private Long articleId;

    /** 标题 */
    private String title;

    /** 图片url */
    private String picUrl;

    /** 外部链接地址 */
    private String linkUrl;

    /** 是否启用 */
    private boolean enabled;

    private int position;

    public Banner() {
    }

    public Banner(BannerType bannerType, Long articleId, String title, String picUrl, String linkUrl, boolean enabled, int position) {
        this.bannerType = bannerType;
        this.articleId = articleId;
        this.title = title;
        this.picUrl = picUrl;
        this.linkUrl = linkUrl;
        this.enabled = enabled;
        this.position = position;
    }

    public int getBannerTag() {
        return bannerTag.ordinal();
    }

    public void setBannerTag(BannerTag bannerTag) {
        this.bannerTag = bannerTag;
    }

    public int getBannerType() {
        return bannerType.ordinal();
    }

    public void setBannerType(BannerType bannerType) {
        this.bannerType = bannerType;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
