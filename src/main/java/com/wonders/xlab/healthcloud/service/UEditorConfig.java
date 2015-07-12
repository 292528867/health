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
	
	/** 执行上传视频的action名称 */
	private String videoActionName;
	/** 提交的视频表单名称 */
	private String videoFieldName;
	/** 上传保存路径,可以自定义保存路径和文件名格式 */
	private String videoPathFormat;
	/** 视频访问路径前缀（放在七牛上） */
	private String videoUrlPrefix;
	/** 上传大小限制，单位B，默认100MB */
	private int videoMaxSize;
	/** 上传视频格式显示 */
	private String[] videoAllowFiles;

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
	public String getVideoActionName() {
		return videoActionName;
	}
	public void setVideoActionName(String videoActionName) {
		this.videoActionName = videoActionName;
	}
	public String getVideoFieldName() {
		return videoFieldName;
	}
	public void setVideoFieldName(String videoFieldName) {
		this.videoFieldName = videoFieldName;
	}
	public String getVideoPathFormat() {
		return videoPathFormat;
	}
	public void setVideoPathFormat(String videoPathFormat) {
		this.videoPathFormat = videoPathFormat;
	}
	public String getVideoUrlPrefix() {
		return videoUrlPrefix;
	}
	public void setVideoUrlPrefix(String videoUrlPrefix) {
		this.videoUrlPrefix = videoUrlPrefix;
	}
	public int getVideoMaxSize() {
		return videoMaxSize;
	}
	public void setVideoMaxSize(int videoMaxSize) {
		this.videoMaxSize = videoMaxSize;
	}
	public String[] getVideoAllowFiles() {
		return videoAllowFiles;
	}
	public void setVideoAllowFiles(String[] videoAllowFiles) {
		this.videoAllowFiles = videoAllowFiles;
	}
	
	
}
