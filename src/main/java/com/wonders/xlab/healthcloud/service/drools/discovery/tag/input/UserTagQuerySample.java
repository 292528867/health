package com.wonders.xlab.healthcloud.service.drools.discovery.tag.input;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户查询样本，用于规则起始。
 * 表示，某个某个用户关心的某个分类标签。
 * @author xu
 *
 */
public class UserTagQuerySample implements Comparable<UserTagQuerySample> {
	/** 用户id */
	private Long userId;
	/** 标签id  */
	private Long tagId;
	/** 标签名字 */
	private String tagName;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(ToStringStyle.MULTI_LINE_STYLE)
			.append("用户id", userId)
			.append("标签id", tagId)
			.append("tag名字", tagName)
			.toString();
	}
	
	@Override
	public int compareTo(UserTagQuerySample sample) {
		return new CompareToBuilder()
			.append(this.userId, sample.userId)
			.append(this.tagId, sample.tagId)
			.toComparison();
	}
}
