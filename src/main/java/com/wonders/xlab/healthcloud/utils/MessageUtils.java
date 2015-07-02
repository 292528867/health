package com.wonders.xlab.healthcloud.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.springframework.util.Assert;

/**
 * 消息工具类。
 * @author xu
 *
 */
public class MessageUtils {
	/** 日志记录器 */
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MessageUtils.class);

    /** 信鸽 android_accessid */
     public  final static  long ANDRIOID_ACCESSID=2100099677;
    /** 信鸽 android secretkey */
     public  final  static  String ANDRIOID_SECRETKEY="e071001de270d2e4e0e150073e7f8d6f";

    /**
     * 发送验证码内容
     */
    public static final String SMS_CODE_CONTENT = "亲爱的妈妈，我们有了团队祝您开心健康。您的短信验证码：";

    /**
     * 标记
     */
    public static final String SMS_MSG_SIGN = "有了";
    
    private MessageUtils() {
    }

    /**
     * 登录时短信验证
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return
     * @throws IOException
     */
    public static int sendMessage(String mobile, String code) {

        return send(mobile, SMS_CODE_CONTENT + code, SMS_MSG_SIGN);
    }
    
    public static void main(String[] args) {
    	System.out.println("测试短信");
    	sendMessage("13795330725", "1111");
    	System.out.println("发送完成");
    }


    public static int send(String mobile, String content, String sign) {

        Assert.notNull(mobile, "手机号不能为空");
        Assert.notNull(content, "短信内容不能为空");
        Assert.notNull(sign, "短信签名不能为空");

        logger.debug("给手机号:{} 发送内容为:{} 的短信", mobile, content.concat(sign));

        BufferedReader reader = null;
        try {

            StringBuffer sb = new StringBuffer("http://sms.1xinxi.cn/asmx/smsservice.aspx?");

            // 向StringBuffer追加用户名
            //sb.append("name=登录名、手机或qq");
            sb.append("name=xinxi");
            // 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
            sb.append("&pwd=2F3F8F235E3C1FCAE51F848AAA72");
            // 向StringBuffer追加手机号码
            sb.append("&mobile=" + mobile);
            // 向StringBuffer追加消息内容转URL标准码
            sb.append("&content=" + URLEncoder.encode(content, "UTF-8"));
            //追加发送时间，可为空，为空为及时发送
            //sb.append("&stime=20");
            //加签名
            sb.append("&sign=" + URLEncoder.encode(sign, "UTF-8"));

            //type为固定值pt  extno为扩展码，必须为数字 可为空
            sb.append("&type=pt&extno=123");
            // 创建url对象
            URL url = new URL(sb.toString());

            // 打开url连接
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            // 设置url请求方式 ‘get’ 或者 ‘post’
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setUseCaches(false);
//            connection.setRequestMethod("POST");

            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // 返回发送结果
            String result = reader.readLine().substring(0, 1);
            // 发送
            // 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
            //Boolean result = Integer.parseInt(inputLine) >0 ? false: true;
            return Integer.parseInt(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
