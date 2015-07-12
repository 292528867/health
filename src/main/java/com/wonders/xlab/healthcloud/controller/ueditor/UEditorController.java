package com.wonders.xlab.healthcloud.controller.ueditor;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wonders.xlab.healthcloud.service.UEditorConfig;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;

@RestController
@RequestMapping(value = "/ue")
@EnableConfigurationProperties(UEditorConfig.class)
public class UEditorController {
	@Autowired
	private UEditorConfig config;
	
	/**
	 * 返回ueditor配置json（默认调用param，action=config）。
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, params = "action=config")
	public UEditorConfig getConfig() {
		return config;
	}
	
	/**
	 * 图片上传，上传到七牛（默认调用param，action=uploadimage，post的file名字为upfile）
	 * @param file
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = "action=uploadimage")
	public UploadImageResult uploadPic(MultipartFile upfile) {
		if (upfile != null && !upfile.isEmpty()) {
			try {
				String fileName = "healthinfo-icon-" + String.valueOf((new Date()).getTime());
				QiniuUploadUtils.upload(upfile.getBytes(), fileName);
				
				// {"url" : "http://7xk3mz.com2.z0.glb.qiniucdn.com/healthinfo-icon-1436684006380"}
				// {"url" : "http://7xk3mz.com2.z0.glb.qiniucdn.com/healthinfo-icon-1436684074278"}
				
				return new UploadImageResult().setState("SUCCESS").setTitle(fileName).setOriginal(fileName).setUrl(fileName);
			} catch (IOException exp) {
				exp.printStackTrace();
				return new UploadImageResult().setState("ERROR");
			}
		} else {
			return new UploadImageResult().setState("ERROR");
		}
	}
	
	public static class UploadImageResult {
		private String state;
		private String url;
		private String title;
		private String original;
		public String getState() {
			return state;
		}
		public UploadImageResult setState(String state) {
			this.state = state;
			return this;
		}
		public String getUrl() {
			return url;
		}
		public UploadImageResult setUrl(String url) {
			this.url = url;
			return this;
		}
		public String getTitle() {
			return title;
		}
		public UploadImageResult setTitle(String title) {
			this.title = title;
			return this;
		}
		public String getOriginal() {
			return original;
		}
		public UploadImageResult setOriginal(String original) {
			this.original = original;
			return this;
		}
	}
	
	
}
