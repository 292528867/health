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
	@NotNull(message = "健康信息不能为空")
	private String htmlInfo;
	
	
	/**
	 * 转换成一个新的{@link HealthInfo}实体对象，临时状态。
	 */
	public HealthInfo toNewHealthInfo(HealthCategory hc) {
		HealthInfo hi = new HealthInfo();
		hi.setHtmlInfo(htmlInfo);
		hi.setHealthCategory(hc);
		return hi;
	}
	
	/**
	 * 根据{@link HealthInfo}实体对象转换成dto对象。
	 * @param hc 游离对象
	 * @return
	 */
	public HealthInfoDto toNewHealthInfoDto(HealthInfo hi) {
		HealthInfoDto dto = new HealthInfoDto();
		dto.setHtmlInfo(hi.getHtmlInfo());
		return dto;
	}
	
	/**
	 * 更新一个{@link HealthInfo}实体对象，游离状态。
	 */
	public HealthInfo updateHealthInfo(HealthInfo hi) {
		hi.setHtmlInfo(htmlInfo);
		return hi;
	}

	public String getHtmlInfo() {
		return htmlInfo;
	}

	public void setHtmlInfo(String htmlInfo) {
		this.htmlInfo = htmlInfo;
	}
	
	
	
}
