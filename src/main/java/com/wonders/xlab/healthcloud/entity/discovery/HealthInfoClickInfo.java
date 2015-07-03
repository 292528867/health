package com.wonders.xlab.healthcloud.entity.discovery;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * 健康信息点击记录。
 * @author xu
 *
 */
@Entity
@Table(name = "HC_HEALTH_INFO_CLICK")
public class HealthInfoClickInfo extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 点击数 */
	private Integer clickCount;
	/** 关联的具体健康信息 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "HEALTHINFO_ID")
	private HealthInfo healthInfo;
	/** 点击日期（格式：yyyy-MM-dd） */
	private Date clickDate;
	
	// TODO：其他字段再议
	
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModifiedDate;

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public HealthInfo getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(HealthInfo healthInfo) {
		this.healthInfo = healthInfo;
	}

	public Date getClickDate() {
		return clickDate;
	}

	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
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
    
}