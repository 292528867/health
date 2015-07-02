package com.wonders.xlab.healthcloud.utils;

import com.bcloud.msg.http.HttpSender;
import org.apache.commons.lang3.StringUtils;

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

    public static int sendValidCode(String mobiles, String validCode) {
        try {
            String content = String.format(SMS_VALID_CODE_CONTENT, validCode);
            String resultCode = HttpSender.batchSend(SMS_SRV_URL, SMS_ACT_USER, SMS_ACT_PWD, mobiles, content, true, null, null);
            String status = resultCode.substring(resultCode.indexOf(",") + 1, resultCode.indexOf(",") + 2);
            if (StringUtils.equals(status, "0")) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String mobiles = "15800691693,13621673989";//手机号码，多个号码使用","分割
        int resultCode = sendValidCode(mobiles, "1234");
        System.out.println("resultCode = " + resultCode);
    }


}
