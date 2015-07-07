package com.wonders.xlab.healthcloud.service.drools.discovery.article.input;

import java.util.Date;

/**
 * 健康信息文章点击样本。
 * @author xu
 *
 */
public class HealthInfoClickSample {
	/** 健康信息文章id */
	private Long id;
	/** 文章创建时间 */
	private Date createTime;
	/** 文章真实总点击量 */
	private Long clickCount;

	public HealthInfoClickSample(Long id, Date createTime, Long clickCount) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.clickCount = clickCount;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}
	
}
