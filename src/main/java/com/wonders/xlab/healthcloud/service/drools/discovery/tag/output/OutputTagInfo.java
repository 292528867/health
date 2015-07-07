package com.wonders.xlab.healthcloud.service.drools.discovery.tag.output;

import java.util.ArrayList;
import java.util.List;

/**
 * 输出的每次推荐的标签组。
 * @author xu
 */
public class OutputTagInfo {
	/** 用户id */
	private Long userId;
	/** 其他描述（暂时不用） */
	private String otherDesc;
	/** 推荐的标签ids */
	private List<Long> pushTagIds = new ArrayList<>();
	/** 推荐的标签对应的标题 */
	private List<String> pushTagNames = new ArrayList<>();
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<Long> getPushTagIds() {
		return pushTagIds;
	}
	public void setPushTagIds(List<Long> pushTagIds) {
		this.pushTagIds = pushTagIds;
	}
	public String getOtherDesc() {
		return otherDesc;
	}
	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}
	public List<String> getPushTagNames() {
		return pushTagNames;
	}
	public void setPushTagNames(List<String> pushTagNames) {
		this.pushTagNames = pushTagNames;
	}
	
}
