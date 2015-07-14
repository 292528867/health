package com.wonders.xlab.healthcloud.entity.discovery;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * 健康信息文章点击记录。
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
	
	/** 实际点击数 */
	private long clickCount;
	/** 虚拟点击数（根据规则计算，一直累加的，期间如果权重改了，之前的数据不更新，需要的话重新计算更新） */
	private long virtualClickCount;
	/** 点击量权重值 */
	@Column(nullable=false)
	private int clickCountA;
	
	/** 关联的具体健康信息 */
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "HEALTH_INFO_ID")
	private HealthInfo healthInfo;
	
	// TODO：其他字段再议
	
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModifiedDate;

	public long getClickCount() {
		return clickCount;
	}

	public void setClickCount(long clickCount) {
		this.clickCount = clickCount;
	}

	public long getVirtualClickCount() {
		return virtualClickCount;
	}

	public void setVirtualClickCount(long virtualClickCount) {
		this.virtualClickCount = virtualClickCount;
	}

	public int getClickCountA() {
		return clickCountA;
	}

	public void setClickCountA(int clickCountA) {
		this.clickCountA = clickCountA;
	}

	public HealthInfo getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(HealthInfo healthInfo) {
		this.healthInfo = healthInfo;
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