package com.wonders.xlab.healthcloud.entity.discovery;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.wonders.xlab.healthcloud.entity.hcpackage.Classification;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * 健康分类（如：糖尿病1，糖尿病2，等等）。
 * @author xu
 */
@Entity
@Table(name = "HC_HEALTH_CATEGORY")
public class HealthCategory extends AbstractPersistable<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 标签 */
	private String tag;
	/** 标题 */
	private String title;
	/** 简述 */
	private String description;
	/** 类别类型（TODO：目前暂时不清楚，纵向、横向分类？） */
	private String type;
	
	/** 1级关联标签ids（逗号连接） */
	private String firstRelatedIds;
	/** 无关联标签ids（逗号连接） */
	private String otherRelatedIds;
	
	@OneToMany(mappedBy = "healthCategory")
	/** 关联的健康信息文章 */
	private Set<HealthInfo> hins = new HashSet<>();
	
	@OneToOne(optional = true, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "HOME_HEALTH_INFO_ID")
	/** 首页健康信息文章 */
	private HealthInfo homeInfo;

	/**
	 * 第二大类分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Classification classification;

	/**
	 * 是否推荐
	 */
	private boolean recommend;

	/**
	 * 推荐值
	 */
	private int recommendValue;

    private String icon;
	
	// TODO：其他字段再议论

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModifiedDate;

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Set<HealthInfo> getHins() {
		return hins;
	}

	public void setHins(Set<HealthInfo> hins) {
		this.hins = hins;
	}

	public HealthInfo getHomeInfo() {
		return homeInfo;
	}

	public void setHomeInfo(HealthInfo homeInfo) {
		this.homeInfo = homeInfo;
	}

	public String getFirstRelatedIds() {
		return firstRelatedIds;
	}

	public void setFirstRelatedIds(String firstRelatedIds) {
		this.firstRelatedIds = firstRelatedIds;
	}

	public String getOtherRelatedIds() {
		return otherRelatedIds;
	}

	public void setOtherRelatedIds(String otherRelatedIds) {
		this.otherRelatedIds = otherRelatedIds;
	}

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
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
}
