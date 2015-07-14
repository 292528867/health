package com.wonders.xlab.healthcloud.service.drools.discovery.article.output;

import java.util.ArrayList;
import java.util.List;

import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;

/**
 * 日显示健康信息输出。
 * @author xu
 */
public class OutputDaytHealthInfo {
	/** 用户id */
	private Long userId;
	/** 显示的健康Infos */
	private List<HealthInfo> boes = new ArrayList<>();
	
	
	public List<HealthInfo> getBoes() {
		return boes;
	}
	public void setBoes(List<HealthInfo> boes) {
		this.boes = boes;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
