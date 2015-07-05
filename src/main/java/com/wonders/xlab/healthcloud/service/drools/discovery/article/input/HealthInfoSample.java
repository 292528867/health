package com.wonders.xlab.healthcloud.service.drools.discovery.article.input;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 健康info，文章样本。
 * @author xu
 *
 */
public class HealthInfoSample implements Comparable<HealthInfoSample> {
	/** 用户id */
	private Long userId;
	/** 健康信息id */
	private Long healthInfoId;
	/** 健康信息标题 */
	private String healthInfoTitle;
	/** 健康信息文章点击次数 */
	private Integer clickCount;

	public HealthInfoSample(Long userId, Long healthInfoId,
			String healthInfoTitle, Integer clickCount) {
		super();
		this.userId = userId;
		this.healthInfoId = healthInfoId;
		this.healthInfoTitle = healthInfoTitle;
		this.clickCount = clickCount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getHealthInfoId() {
		return healthInfoId;
	}
	public void setHealthInfoId(Long healthInfoId) {
		this.healthInfoId = healthInfoId;
	}
	
	public String getHealthInfoTitle() {
		return healthInfoTitle;
	}
	public void setHealthInfoTitle(String healthInfoTitle) {
		this.healthInfoTitle = healthInfoTitle;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(ToStringStyle.MULTI_LINE_STYLE)
			.append("用户id", userId)
			.append("健康信息id", healthInfoId)
			.append("健康信息标题", healthInfoTitle)
			.append("健康信息文章点击次数", clickCount)
			.toString();
		
	}
	
	@Override
	public int compareTo(HealthInfoSample sample) {
		return new CompareToBuilder()
			.append(this.clickCount, sample.clickCount)
			.toComparison();
	}
}
