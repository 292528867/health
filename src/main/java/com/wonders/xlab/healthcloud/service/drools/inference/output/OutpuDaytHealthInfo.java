package com.wonders.xlab.healthcloud.service.drools.inference.output;

/**
 * 日显示健康信息输出。
 * @author xu
 */
public class OutpuDaytHealthInfo {
	/** 用户id */
	private Long userId;
	/** 显示的健康信息个数 */
	private Integer healthInfoCount;
	/** 显示的健康Info实体ids */
	private Long[] healthInfoIds;
	
	public Integer getHealthInfoCount() {
		return healthInfoCount;
	}
	public void setHealthInfoCount(Integer healthInfoCount) {
		this.healthInfoCount = healthInfoCount;
	}
	public Long[] getHealthInfoIds() {
		return healthInfoIds;
	}
	public void setHealthInfoIds(Long[] healthInfoIds) {
		this.healthInfoIds = healthInfoIds;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
