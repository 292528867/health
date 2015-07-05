package com.wonders.xlab.healthcloud.dto.discovery;

import java.util.List;

/**
 * 关联分类下拉框分类输出dto（用于显示）。
 * @author xu
 *
 */
public class RelatedCategoryComboDto {
	/** TODO：主分类，暂时还没定 */
	private String type;
	/** TODO：指定主分类下的分类 */
	private List<HealthCategoryDto> categories;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<HealthCategoryDto> getCategories() {
		return categories;
	}
	public void setCategories(List<HealthCategoryDto> categories) {
		this.categories = categories;
	}
}
