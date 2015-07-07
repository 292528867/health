package com.wonders.xlab.healthcloud.service.drools.discovery.article.output;

/**
 * 计算输出的点击数对象。
 * @author xu
 *
 */
public class OutputHealthInfoClickCount {
	/** 文章id */
	private Long id;
	/** 重新计算后的点击数 */
	private Long count;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
