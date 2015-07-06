package com.wonders.xlab.healthcloud.entity.banner;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/6.
 */
@Entity
@Table(name = "hc_banner")
public class Banner extends AbstractBaseEntity<Long> {

    /** 标语标签 0 日程 1 发现 2 管家 3 问诊 */
    @Enumerated
    private BannerTag bannerTag;

    /** 标语位置 0 上 1 下 */
    @Enumerated
    private BannerType bannerType;

    /** 文章id */
    private Long articleId;

    /** 标题 */
    private String title;

    /** 图片url */
    private String picUrl;

    /** 外部链接地址 */
    private String linkUrl;

    public Banner() {
    }

    public Banner(BannerTag bannerTag, BannerType bannerType, Long articleId, String title, String picUrl, String linkUrl) {
        this.bannerTag = bannerTag;
        this.bannerType = bannerType;
        this.articleId = articleId;
        this.title = title;
        this.picUrl = picUrl;
        this.linkUrl = linkUrl;
    }

    public BannerTag getBannerTag() {
        return bannerTag;
    }

    public void setBannerTag(BannerTag bannerTag) {
        this.bannerTag = bannerTag;
    }

    public BannerType getBannerType() {
        return bannerType;
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
}
