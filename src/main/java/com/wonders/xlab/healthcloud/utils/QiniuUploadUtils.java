package com.wonders.xlab.healthcloud.utils;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wonders.xlab.healthcloud.dto.QiniuRet;

import java.io.IOException;

/**
 * 上传图片到七牛云存储
 * @author yukui
 */
public class QiniuUploadUtils {

    /**
     * AK
     */
	private static String ACCESS_KEY = "yLpWgvVh5np4VkDwuxnVzRe8M5cB-9fXWl2EYrWi";

    /**
     * SK
     */
	private static String SECRET_KEY = "idB3vbPuyPPszN4D5Rz-QYts9RBbo2xcVVbXmFza";
<<<<<<< HEAD
	private static String url = "http://7xk3mz.com2.z0.glb.qiniucdn.com";
	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
=======

    /**
     * 命名空间url
     */

	private static String url = "http://7xj64q.com2.z0.glb.qiniucdn.com/";

    /**
     * 命名空间
     */
    private static String data = "health-cloud";

    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
>>>>>>> efb7dee60514d8dc9c9d17e7bf87fdb851864372

	public static String upload(byte byteData[], String fileName) {

        String address = null;

        try {

			UploadManager uploadManager = new UploadManager();

			Response res = uploadManager.put(byteData, fileName, getUpToken(fileName));

			QiniuRet ret = res.jsonToObject(QiniuRet.class);

			address = url + ret.key;

		} catch (IOException e) {
			e.printStackTrace();
		}
        return address;
	}

    /**
     * 覆盖上传
     */
	public static String getUpToken(String fileName) {

		return auth.uploadToken(data, fileName);

	}

}
