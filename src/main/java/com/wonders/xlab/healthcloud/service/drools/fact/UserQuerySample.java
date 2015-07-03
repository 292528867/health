package com.wonders.xlab.healthcloud.service.drools.fact;

/**
 * 用户查询样本，用于分组。
 * @author xu
 *
 */
public class UserQuerySample {
	/** 用户id */
	private Long userId;

	public UserQuerySample(Long userId) {
		super();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
