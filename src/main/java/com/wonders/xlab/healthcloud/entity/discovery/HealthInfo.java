package com.wonders.xlab.healthcloud.entity.discovery;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 健康信息。
 * @author xu
 *
 */
@Entity
@Table(name = "HC_HEALTH_INFO")
public class HealthInfo extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 标题 */
	private String title;
	/** 图片url */
	private String pictureUrl;
	/** banner图片url */
	private String pictureUrl2;
	/** 简述 */
	private String description;
	
	/** html页面信息，暂定长度 */
	@Lob
	private String htmlInfo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "HEALTHCATEGORY_ID")
	private HealthCategory healthCategory;
	
	/** 点击信息 */
	@JsonIgnore
	@OneToOne(mappedBy="healthInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private HealthInfoClickInfo healthInfoClickInfo;
	
	
	/** 作为发布时间使用 */
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModifiedDate;

	public String getHtmlInfo() {
		return htmlInfo;
	}

	public void setHtmlInfo(String htmlInfo) {
		this.htmlInfo = htmlInfo;
	}

	public HealthCategory getHealthCategory() {
		return healthCategory;
	}

	public void setHealthCategory(HealthCategory healthCategory) {
		this.healthCategory = healthCategory;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPictureUrl2() {
		return pictureUrl2;
	}

	public void setPictureUrl2(String pictureUrl2) {
		this.pictureUrl2 = pictureUrl2;
	}

	public HealthInfoClickInfo getHealthInfoClickInfo() {
		return healthInfoClickInfo;
	}

	public void setHealthInfoClickInfo(HealthInfoClickInfo healthInfoClickInfo) {
		this.healthInfoClickInfo = healthInfoClickInfo;
	}
	
}
