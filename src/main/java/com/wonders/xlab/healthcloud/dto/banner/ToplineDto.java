package com.wonders.xlab.healthcloud.dto.banner;

import com.wonders.xlab.healthcloud.entity.banner.Topline;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by mars on 15/7/15.
 */
public class ToplineDto {

    /** 图片 */
    @NotNull(message = "图片地址不能为空")
    private String picUrl;

    /** 链接 */
    @NotNull(message = "链接地址不能为空")
    private String linkUrl;

    /** 启用 */
    @NotNull(message = "启用不能为空")
    @Pattern(regexp = "^true|false$", message = "启用必须为true|false")
    private String enabled;

    public Topline toNewTopline() {
        Topline topline = new Topline();
        topline.setPicUrl(picUrl);
        topline.setLinkUrl(linkUrl);
        topline.setEnabled(Boolean.valueOf(enabled));
        return topline;
    }

    public Topline updateTopline(Topline topline) {
        topline.setPicUrl(picUrl);
        topline.setLinkUrl(linkUrl);
        topline.setEnabled(Boolean.valueOf(enabled));
        return topline;
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

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
