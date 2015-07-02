package com.wonders.xlab.healthcloud.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wonders.xlab.healthcloud.controller.dto.QiniuRet;

/**
 * 上传图片到七牛云存储
 * 
 * @author yukui
 *
 */
public class QiniuUploadUtils {
	private static Logger log = LoggerFactory.getLogger(QiniuUploadUtils.class);
	private static String ACCESS_KEY = "yLpWgvVh5np4VkDwuxnVzRe8M5cB-9fXWl2EYrWi";
	private static String SECRET_KEY = "idB3vbPuyPPszN4D5Rz-QYts9RBbo2xcVVbXmFza";
	private static String url = "http://7xj64q.com2.z0.glb.qiniucdn.com/";
	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	public static String upload(byte byteData[], String fileName) {
		try {
			UploadManager uploadManager = new UploadManager();
			Response res = uploadManager.put(byteData, fileName,
					getUpToken(fileName));
			QiniuRet ret = res.jsonToObject(QiniuRet.class);
			String address = url + ret.key;
			return address;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时简单状态信息
			log.error(r.toString());
			try {
				// 响应的文本信息
				log.error(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 简单上传，使用默认策略
	public static String getUpToken0() {
		return auth.uploadToken("revivesh");
	}

	// 覆盖上传
	public static String getUpToken(String fileName) {
		return auth.uploadToken("revivesh", fileName);
	}

	public static void main(String[] args) {
		// QiniuUploadUtil.upload();
	}
}
