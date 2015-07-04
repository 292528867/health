package com.wonders.xlab.healthcloud.dto.discovery;

import javax.validation.constraints.NotNull;

import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;

/**
 * {@link HealthCategory} dto对象。
 * @author xu
 *
 */
public class HealthCatagoryDto {
	@NotNull(message = "分类标题不能为空")
	private String title;
	@NotNull(message = "分类描述不能为空")
	private String desc;
	/** 预留字段 */
	private String type;
	
	/**
	 * 转换成一个新的{@link HealthCategory}实体对象，临时状态。
	 */
	public HealthCategory toNewHealthCategory() {
		HealthCategory hc = new HealthCategory();
		hc.setTitle(title);
		hc.setDescription(desc);
		hc.setType(type);
		return hc;
	}
	/**
	 * 根据{@link HealthCategory}实体对象转换成dto对象。
	 * @param hc 游离对象
	 * @return
	 */
	public HealthCatagoryDto toNewHealthCategoryDto(HealthCategory hc) {
		HealthCatagoryDto dto = new HealthCatagoryDto();
		dto.setTitle(hc.getTitle());
		dto.setDesc(hc.getDescription());
		dto.setType(hc.getType());
		return dto;
	}
	
	/**
	 * 更新一个{@link HealthCategory}实体对象，游离状态。
	 */
	public HealthCategory updateHealthCategory(HealthCategory hc) {
		hc.setTitle(title);
		hc.setDescription(desc);
		hc.setType(type);
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
	
	
}
