package com.wonders.xlab.healthcloud.service.drools.discovery.article.input;


/**
 * 健康信息文章点击样本（简单包装一个HealthInfoSample）。
 * @author xu
 *
 */
public class HealthInfoClickSample {
	
	private HealthInfoSample healInfoSample;

	public HealthInfoClickSample(HealthInfoSample healInfoSample) {
		super();
		this.healInfoSample = healInfoSample;
	}

	public HealthInfoSample getHealInfoSample() {
		return healInfoSample;
	}

	public void setHealInfoSample(HealthInfoSample healInfoSample) {
		this.healInfoSample = healInfoSample;
	}
	
}
