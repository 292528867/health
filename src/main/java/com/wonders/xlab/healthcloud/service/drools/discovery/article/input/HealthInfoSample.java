package com.wonders.xlab.healthcloud.service.drools.discovery.article.input;

import java.util.Date;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;


/**
 * 健康info，文章样本。
 * @author xu
 *
 */
public class HealthInfoSample implements Comparable<HealthInfoSample> {
	/** 用户id */
	private Long userId;
	/** 创建时间 */
	private Date createTime;
	/** 健康信息id */
	private Long healthInfoId;
	/** 健康信息标题 */
	private String healthInfoTitle;
	/** 健康信息文章点击次数 */
	private Long clickCount;
	
	/** 业务对象 */
	private HealthInfo bo;

	public HealthInfoSample(Long userId, Date createTime, Long healthInfoId,
			String healthInfoTitle, Long clickCount, HealthInfo bo) {
		super();
		this.userId = userId;
		this.createTime = createTime;
		this.healthInfoId = healthInfoId;
		this.healthInfoTitle = healthInfoTitle;
		this.clickCount = clickCount;
		this.bo = bo;
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
	
	public Long getClickCount() {
		return clickCount;
	}
	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	public HealthInfo getBo() {
		return bo;
	}
	public void setBo(HealthInfo bo) {
		this.bo = bo;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(ToStringStyle.MULTI_LINE_STYLE)
			.append("用户id", userId)
			.append("创建时间", createTime)
			.append("健康信息id", healthInfoId)
			.append("健康信息标题", healthInfoTitle)
			.append("健康信息文章点击次数", clickCount)
			.toString();
		
	}
	
	@Override
	public int compareTo(HealthInfoSample sample) {
		return new CompareToBuilder()
			.append(this.clickCount, sample.clickCount)
			.append(this.createTime, sample.createTime)
			.toComparison();
	}
	
}
