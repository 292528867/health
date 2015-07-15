package com.wonders.xlab.healthcloud.dto.banner;

import com.wonders.xlab.healthcloud.entity.banner.Topline;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/15.
 */
public class ToplineDto {

    private String name;

    /** 图片 */
    @NotNull(message = "图片地址不能为空")
    private String picUrl;

    /** 链接 */
    @NotNull(message = "链接地址不能为空")
    private String linkUrl;

    /** 启用 */
//    @NotNull(message = "启用不能为空")
    private Boolean enabled;

    /** 排序 倒叙 */
    @NotNull(message = "排序不能为空")
    private Integer position;

    public Topline toNewTopline() {
        Topline topline = new Topline();
        topline.setName(name);
        topline.setPicUrl(picUrl);
        topline.setLinkUrl(linkUrl);
        topline.setEnabled(enabled);
        topline.setPosition(position);
        return topline;
    }

    public Topline updateTopline(Topline topline) {
        topline.setName(name);
        topline.setPicUrl(picUrl);
        topline.setLinkUrl(linkUrl);
        topline.setPosition(position);
        return topline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
