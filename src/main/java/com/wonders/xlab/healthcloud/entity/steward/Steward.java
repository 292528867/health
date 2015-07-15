package com.wonders.xlab.healthcloud.entity.steward;

import com.wonders.xlab.healthcloud.entity.BaseInfo;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Entity
@Table(name = "HC_STEWARD")
public class Steward extends BaseInfo<Long> {

    /**
     * 管家等级
     */
    @Enumerated
    private Rank rank;

    public enum Rank {
        //        S, A, B, C
        钻石级管家, 金牌级管家, 银牌级管家, 铜牌级管家
    }

    /**
     * 管家分数
     */
    private int stewardIntegration;

    /**
     * 管家服务天数
     */
    private int servicedPeriod;

    /**
     * 执照
     */
    private String License;

    /**
     * 位置
     */
    @Enumerated
    private Location location;

    /**
     * 图标颜色
     */
    private String color;
    /**
     * levelUrl
     *
     * @return
     */
    private String levelUrl;

    /**
     * 微信号
     *
     * @return
     */
    private String weChat;

    /**
     * 客服电话
     */
    private String serviceTel;

    /**
     * 明星服务
     */
    @Transient
    private List<Map<String, Object>> starService = new ArrayList<>();


    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public int getStewardIntegration() {
        return stewardIntegration;
    }

    public void setStewardIntegration(int stewardIntegration) {
        this.stewardIntegration = stewardIntegration;
    }

    public int getServicedPeriod() {
        return servicedPeriod;
    }

    public void setServicedPeriod(int servicedPeriod) {
        this.servicedPeriod = servicedPeriod;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLevelUrl() {
        return levelUrl;
    }

    public void setLevelUrl(String levelUrl) {
        this.levelUrl = levelUrl;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public List<Map<String, Object>> getStarService() {
        return starService;
    }

    public void setStarService(List<Map<String, Object>> starService) {
        this.starService = starService;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }
}
