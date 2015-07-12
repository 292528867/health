package com.wonders.xlab.healthcloud.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * UEditoer配置。
 * @author xu
 *
 */
@ConfigurationProperties(
	locations = "classpath:ueditor/config.properties",
	ignoreInvalidFields = true,
	prefix = "ue"
)
public class UEditorConfig {
	/** 执行上传图片的action名称 */
	private String imageActionName;
	/** 提交的图片表单名称 */
	private String imageFieldName;
	/** 上传大小限制，单位B */
	private int imageMaxSize;
	/** 上传图片格式显示 */
	private String[] imageAllowFiles;
	/** 是否压缩图片,默认是true */
	private boolean imageCompressEnable;
	/** 图片压缩最长边限制 */
	private int imageCompressBorder;
	/** 插入的图片浮动方式 */
	private String imageInsertAlign;
	/** 图片访问路径前缀 */
	private String imageUrlPrefix;
	/** 上传保存路径,可以自定义保存路径和文件名格式 */
	private String imagePathFormat;
	public String getImageActionName() {
		return imageActionName;
	}
	public void setImageActionName(String imageActionName) {
		this.imageActionName = imageActionName;
	}
	public String getImageFieldName() {
		return imageFieldName;
	}
	public void setImageFieldName(String imageFieldName) {
		this.imageFieldName = imageFieldName;
	}
	public int getImageMaxSize() {
		return imageMaxSize;
	}
	public void setImageMaxSize(int imageMaxSize) {
		this.imageMaxSize = imageMaxSize;
	}
	public String[] getImageAllowFiles() {
		return imageAllowFiles;
	}
	public void setImageAllowFiles(String[] imageAllowFiles) {
		this.imageAllowFiles = imageAllowFiles;
	}
	public boolean isImageCompressEnable() {
		return imageCompressEnable;
	}
	public void setImageCompressEnable(boolean imageCompressEnable) {
		this.imageCompressEnable = imageCompressEnable;
	}
	public int getImageCompressBorder() {
		return imageCompressBorder;
	}
	public void setImageCompressBorder(int imageCompressBorder) {
		this.imageCompressBorder = imageCompressBorder;
	}
	public String getImageInsertAlign() {
		return imageInsertAlign;
	}
	public void setImageInsertAlign(String imageInsertAlign) {
		this.imageInsertAlign = imageInsertAlign;
	}
	public String getImageUrlPrefix() {
		return imageUrlPrefix;
	}
	public void setImageUrlPrefix(String imageUrlPrefix) {
		this.imageUrlPrefix = imageUrlPrefix;
	}
	public String getImagePathFormat() {
		return imagePathFormat;
	}
	public void setImagePathFormat(String imagePathFormat) {
		this.imagePathFormat = imagePathFormat;
	}
	
	
}
