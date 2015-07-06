package com.wonders.xlab.healthcloud.dto.discovery;

import javax.validation.constraints.NotNull;

import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;

/**
 * {@link HealthInfo}dto。
 * @author xu
 *
 */
public class HealthInfoDto {
	@NotNull(message = "标题不能为空")
	private String title;
	@NotNull(message = "图片不能为空")
	private String pictureUrl;
	@NotNull(message = "简述不能为空")
	private String desc;
	@NotNull(message = "健康信息不能为空")
	private String htmlInfo;
	
	/** 点击量 */
	private Long clickCount;
	/** 文章标题id */
	private Long Id;
	
	/**
	 * 转换成一个新的{@link HealthInfo}实体对象，临时状态。
	 */
	public HealthInfo toNewHealthInfo(HealthCategory hc) {
		HealthInfo hi = new HealthInfo();
		hi.setTitle(title);
		hi.setPictureUrl(pictureUrl);
		hi.setDescription(desc);
		hi.setHtmlInfo(htmlInfo);
		hi.setHealthCategory(hc);
		return hi;
	}
	
	/**
	 * 根据{@link HealthInfo}实体对象转换成dto对象。
	 * @param hi 游离对象
	 * @return
	 */
	public HealthInfoDto toNewHealthInfoDto(HealthInfo hi) {
		HealthInfoDto dto = new HealthInfoDto();
		dto.setHtmlInfo(hi.getHtmlInfo());
		dto.setDesc(hi.getDescription());
		dto.setTitle(hi.getTitle());
		dto.setPictureUrl(hi.getPictureUrl());
		dto.setId(hi.getId());
		return dto;
	}
	
	/**
	 * 更新一个{@link HealthInfo}实体对象，游离状态。
	 */
	public HealthInfo updateHealthInfo(HealthInfo hi) {
		hi.setTitle(title);
		hi.setPictureUrl(pictureUrl);
		hi.setDescription(desc);
		hi.setHtmlInfo(htmlInfo);
		return hi;
	}

	public String getHtmlInfo() {
		return htmlInfo;
	}

	public void setHtmlInfo(String htmlInfo) {
		this.htmlInfo = htmlInfo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}	
	
}
