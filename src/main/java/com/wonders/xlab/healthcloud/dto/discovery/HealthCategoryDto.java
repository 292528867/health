package com.wonders.xlab.healthcloud.dto.discovery;

import javax.validation.constraints.NotNull;

import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;

/**
 * {@link HealthCategory} dto对象。
 * @author xu
 *
 */
public class HealthCategoryDto {
	@NotNull(message = "标题不能为空")
	private String tag;
	@NotNull(message = "分类标题不能为空")
	private String title;
	@NotNull(message = "分类描述不能为空")
	private String desc;
	
	private String firstRelatedIds;
	private String secondRelatedIds;
	
	/** 预留字段 */
	private String type;
	/** id */
	private Long id;
	
	/**
	 * 转换成一个新的{@link HealthCategory}实体对象，临时状态。
	 */
	public HealthCategory toNewHealthCategory() {
		HealthCategory hc = new HealthCategory();
		hc.setTitle(title);
		hc.setDescription(desc);
		hc.setType(type);
		hc.setTag(tag);
		hc.setFirstRelatedIds(firstRelatedIds);
		hc.setSecondRelatedIds(secondRelatedIds);
		return hc;
	}
	/**
	 * 根据{@link HealthCategory}实体对象转换成dto对象。
	 * @param hc 游离对象
	 * @return
	 */
	public HealthCategoryDto toNewHealthCategoryDto(HealthCategory hc) {
		HealthCategoryDto dto = new HealthCategoryDto();
		dto.setTitle(hc.getTitle());
		dto.setDesc(hc.getDescription());
		dto.setType(hc.getType());
		dto.setTag(hc.getTag());
		dto.setId(hc.getId());
		dto.setFirstRelatedIds(hc.getFirstRelatedIds());
		dto.setSecondRelatedIds(hc.getSecondRelatedIds());
		return dto;
	}
	
	/**
	 * 更新一个{@link HealthCategory}实体对象，游离状态。
	 */
	public HealthCategory updateHealthCategory(HealthCategory hc) {
		hc.setTitle(title);
		hc.setDescription(desc);
		hc.setType(type);
		hc.setTag(tag);
		hc.setFirstRelatedIds(firstRelatedIds);
		hc.setSecondRelatedIds(secondRelatedIds);
		return hc;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getFirstRelatedIds() {
		return firstRelatedIds;
	}
	public void setFirstRelatedIds(String firstRelatedIds) {
		this.firstRelatedIds = firstRelatedIds;
	}
	public String getSecondRelatedIds() {
		return secondRelatedIds;
	}
	public void setSecondRelatedIds(String secondRelatedIds) {
		this.secondRelatedIds = secondRelatedIds;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
