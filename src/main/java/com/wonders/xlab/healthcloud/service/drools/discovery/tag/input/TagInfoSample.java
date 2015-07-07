package com.wonders.xlab.healthcloud.service.drools.discovery.tag.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 标签信息样本。
 * @author xu
 *
 */
public class TagInfoSample implements Comparable<TagInfoSample> {
	/** 标签id */
	private Long id;
	/** 标签名字 */
	private String tagName;
	/** 1级关联信息样本ids */
	private List<Long> relation_1_ids = new ArrayList<>();
	/** 无关联信息样本ids */
	private List<Long> no_relation_ids = new ArrayList<>();
	
	public TagInfoSample(Long id, String tagName, List<Long> relation_1_ids,
			List<Long> no_relation_ids) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.relation_1_ids = relation_1_ids;
		this.no_relation_ids = no_relation_ids;
	}
	public TagInfoSample() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public List<Long> getRelation_1_ids() {
		return relation_1_ids;
	}
	public void setRelation_1_ids(List<Long> relation_1_ids) {
		this.relation_1_ids = relation_1_ids;
	}
	public List<Long> getNo_relation_ids() {
		return no_relation_ids;
	}
	public void setNo_relation_ids(List<Long> no_relation_ids) {
		this.no_relation_ids = no_relation_ids;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		return new ToStringBuilder(ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("tag名字", tagName)
			.append("1级关联信息样本ids", StringUtils.join(relation_1_ids))
			.append("无关联信息样本ids", StringUtils.join(no_relation_ids))
			.toString();
	}
	
	@Override
	public int compareTo(TagInfoSample sample) {
		return new CompareToBuilder()
			.append(this.id, sample.id)
			.toComparison();
	}
	
	public static void main(String args[]) {
		Integer[] ids1 = new Integer[] {1,2,3};
		Integer[] ids2 = new Integer[] {5,2};
		List<Integer> list1 = new ArrayList<>();
		list1.addAll(Arrays.asList(ids1));
		List<Integer> list2 = new ArrayList<>();
		list2.addAll(Arrays.asList(ids2));
		System.out.println(list2.removeAll(list1));
		System.out.println(list2);
	}
}
