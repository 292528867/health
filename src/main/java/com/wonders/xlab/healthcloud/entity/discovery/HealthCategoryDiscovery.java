package com.wonders.xlab.healthcloud.entity.discovery;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.wonders.xlab.healthcloud.entity.customer.User;

/**
 * 健康信息文章分裂标签推荐。
 * @author xu
 *
 */
public class HealthCategoryDiscovery extends AbstractPersistable<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/** 关联的用户 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	/** 发现日期（yyyy-MM-dd） */
	private Date discoveryDate;
	
	/** 推荐的分类ids（以逗号连接的id字符串） */
	private String discoveryHealthCategoryIds;
	
	/** 作为发布时间使用 */
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModifiedDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDiscoveryDate() {
		return discoveryDate;
	}

	public void setDiscoveryDate(Date discoveryDate) {
		this.discoveryDate = discoveryDate;
	}

	public String getDiscoveryHealthCategoryIds() {
		return discoveryHealthCategoryIds;
	}

	public void setDiscoveryHealthCategoryIds(String discoveryHealthCategoryIds) {
		this.discoveryHealthCategoryIds = discoveryHealthCategoryIds;
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
