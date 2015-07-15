package com.wonders.xlab.healthcloud.dto.banner;

import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.banner.BannerTag;
import com.wonders.xlab.healthcloud.entity.banner.BannerType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by mars on 15/7/6.
 */
public class BannerDto {

    private Long id;

    /** Banner标签 0 计划 1 发现 2 管家 3 问诊 4 商城 5 个人中心 6 购药 */
    @NotNull(message = "标签不能为空")
//    @Pattern(regexp = "^0|1|2|3|4|5|6$", message = "标签必须为 0日程｜1发现｜2管家｜3问诊｜4商城｜5个人中心｜6购药")
    private String bannerTag;

    /** 标语位置 0 上 1 下 */
    @NotNull(message = "标签位置不能为空")
    @Pattern(regexp = "^0|1$", message = "标签位置必须为 0上｜1下")
    private String bannerType;

    /** 文章id */
//    @NotNull(message = "文章不能为空")
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

    @NotNull(message = "位置不能空")
    private String position;

    @NotNull(message = "启用位不能为空")
    @Pattern(regexp = "^true|false$", message = "启用必须为 true｜false")
    private String enabled;

    /** 文章标题 */
    private String articleTitle;

    public Banner toNewBanner() {
        Banner banner = new Banner(
                BannerType.values()[NumberUtils.toInt(bannerType)],
                articleId,
                title,
                picUrl,
                linkUrl,
                Boolean.valueOf(enabled),
                Integer.valueOf(position)
        );
        if (!StringUtils.equals(bannerTag, "none")) {
            banner.setBannerTag(BannerTag.values()[NumberUtils.toInt(bannerTag)]);
        }
        return banner;
    }

    public Banner updateBanner(Banner banner) {
        if (!StringUtils.equals(bannerTag, "none")) {
            banner.setBannerTag(BannerTag.values()[NumberUtils.toInt(bannerTag)]);
        }
        banner.setBannerType(BannerType.values()[NumberUtils.toInt(bannerType)]);
        banner.setArticleId(articleId);
        banner.setTitle(title);
        banner.setPicUrl(picUrl);
        banner.setLinkUrl(linkUrl);
        banner.setEnabled(Boolean.valueOf(enabled));
        banner.setPosition(NumberUtils.toInt(position));
        return banner;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}
