package com.wonders.xlab.healthcloud.utils;

import com.bcloud.msg.http.HttpSender;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wangqiang on 15/7/2.
 */
public class SmsUtils {

    public static final String SMS_SRV_URL = "http://222.73.117.158/msg/";
    public static final String SMS_ACT_USER = "wan-01";
    public static final String SMS_ACT_PWD = "Txb123456";
    public static final String SMS_VALID_CODE_CONTENT = "验证码为%s。我为你感到高兴，你开始更关注自己健康了。别急，20分钟内输入还是有效的。";
    public static final String SMS_DOCTOR_REPLY_CONTENT = "Hi~你之前的提问特聘专家已作出详尽解答啦，赶紧查阅吧！健康云，时刻与你一起关注健康。";
    public static final String SMS_INVITE_FRIEND_CONTENT = "你的朋友%s正在使用健康云，感觉加入吧";

    private SmsUtils() {

    }

    /**
     * 发送验证码
     * @param mobiles
     * @param validCode
     * @return
     */
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

    /**
     * 发送回复提示信息
     * @param mobiles
     * @return
     */
    public static  int sendEmReplyInfo(String mobiles) {
        try {

            String resultCode = HttpSender.batchSend(SMS_SRV_URL, SMS_ACT_USER, SMS_ACT_PWD, mobiles, SMS_DOCTOR_REPLY_CONTENT, true, null, null);
            String status = resultCode.substring(resultCode.indexOf(",") + 1, resultCode.indexOf(",") + 2);
            if (StringUtils.equals(status, "0")) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int inviteFriend(String friendName,String mobiles){
        try {
            String content = String.format(SMS_INVITE_FRIEND_CONTENT, friendName);
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

    public static void main(String[] args) throws Exception{
        String mobiles = "15021470585";//手机号码，多个号码使用","分割
      //  int resultCode = sendValidCode(mobiles, "1234");
        int resultCode = sendEmReplyInfo(mobiles);
        System.out.println("resultCode = " + resultCode);
    }


}
