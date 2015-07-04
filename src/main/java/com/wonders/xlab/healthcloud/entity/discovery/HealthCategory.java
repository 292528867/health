package com.wonders.xlab.healthcloud.entity.discovery;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;

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
    
    
}
