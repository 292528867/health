package com.wonders.xlab.healthcloud.service.drools.discovery.article.input;

import java.util.Date;


/**
 * 健康信息文章点击样本。
 * @author xu
 *
 */
public class HealthInfoClickSample {
	/** healthInfo id */
	private Long id;
	/** 创建时间 */
	private Date createTime;
	/** 点击数 */
	private long clickCount;
	/** 点击量权重值 */
	private int clickCount_A;
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
	public long getClickCount() {
		return clickCount;
	}
	public void setClickCount(long clickCount) {
		this.clickCount = clickCount;
	}
	
	public int getClickCount_A() {
		return clickCount_A;
	}
	public void setClickCount_A(int clickCount_A) {
		this.clickCount_A = clickCount_A;
	}
	public HealthInfoClickSample(Long id, Date createTime, long clickCount,
			int clickCount_A) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.clickCount = clickCount;
		this.clickCount_A = clickCount_A;
	}
	
	
	
}
