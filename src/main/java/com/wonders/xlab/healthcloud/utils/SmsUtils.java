package com.wonders.xlab.healthcloud.utils;

import com.bcloud.msg.http.HttpSender;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by wangqiang on 15/7/2.
 */
public class SmsUtils {

    public static final String SMS_SRV_URL = "http://222.73.117.158/msg/";
    public static final String SMS_ACT_USER = "jiekou-clcs-01";
    public static final String SMS_ACT_PWD = "Tch498965";
    public static final String SMS_VALID_CODE_CONTENT = "亲爱的用户，您的验证码是%s，5分钟内有效。";

    private SmsUtils() {

    }

    public static String sendValidCode(String mobiles) {
        try {
//            String validCode = RandomStringUtils.random(4);
            return HttpSender.batchSend(SMS_SRV_URL, SMS_ACT_USER, SMS_ACT_PWD, mobiles, SMS_VALID_CODE_CONTENT, true, null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String mobiles = "15800691693,13621673989";//手机号码，多个号码使用","分割
        String resultCode = sendValidCode(mobiles);
        System.out.println("resultCode = " + resultCode);
    }


}
