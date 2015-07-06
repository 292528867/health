package com.wonders.xlab.healthcloud.dto.banner;

import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.banner.BannerTag;
import com.wonders.xlab.healthcloud.entity.banner.BannerType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by mars on 15/7/6.
 */
public class BannerDto {

    /** 标语标签 0 日程 1 发现 2 管家 3 问诊 */
    @NotNull(message = "标签不能为空")
    @Pattern(regexp = "^0|1|2|3$", message = "标签必须为 0日程｜1发现｜2管家｜3问诊")
    private String bannerTag;

    /** 标语位置 0 上 1 下 */
    @NotNull(message = "标签位置不能为空")
    @Pattern(regexp = "^0|1$", message = "标签位置必须为 0上｜1下")
    private String bannerType;

    /** 文章id */
    @NotNull(message = "文章不能为空")
    private Long articleId;

    /** 标题 */
    @NotNull(message = "标题不能为空")
    private String title;

    /** 图片url */
    @NotNull(message = "图片不能为空")
    private String picUrl;

    /** 外部链接地址 */
    @NotNull(message = "链接不能为空")
    private String linkUrl;

    @NotNull(message = "启用位不能为空")
        private Boolean enabled;

    public Banner toNewBanner() {
        Banner banner = new Banner(
                BannerTag.values()[Integer.parseInt(bannerTag)],
                BannerType.values()[Integer.parseInt(bannerType)],
                articleId,
                title,
                picUrl,
                linkUrl,
                enabled
        );
        return banner;
    }

    public Banner updateBanner(Banner banner) {
        banner.setBannerTag(BannerTag.values()[Integer.parseInt(bannerTag)]);
        banner.setBannerType(BannerType.values()[Integer.parseInt(bannerType)]);
        banner.setArticleId(articleId);
        banner.setTitle(title);
        banner.setPicUrl(picUrl);
        banner.setLinkUrl(linkUrl);
        banner.setEnabled(enabled);
        return banner;
    }

    public String getBannerTag() {
        return bannerTag;
    }

    public void setBannerTag(String bannerTag) {
        this.bannerTag = bannerTag;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}