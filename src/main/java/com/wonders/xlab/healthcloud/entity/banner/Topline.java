package com.wonders.xlab.healthcloud.entity.banner;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/15.
 */
@Entity
@Table(name = "hc_topline")
public class Topline extends AbstractBaseEntity<Long>{

    /** 头条图片 */
    private String picUrl;

    /** 链接 */
    private String linkUrl;

    /** 启用 */
    private boolean enabled;

    /** 排序 倒叙 */
    private int position;

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
