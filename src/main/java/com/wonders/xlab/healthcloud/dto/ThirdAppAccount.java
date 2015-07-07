package com.wonders.xlab.healthcloud.dto;

/**
 * 第三方app登录需要的信息合集
 * Created by wukai on 15/7/6.
 */
public class ThirdAppAccount {

    /**
     * 用户账号
     */
    private String strUserName;

    /**
     * 明文密码
     */
    private String strPwd;

    /**
     * 密文密码
     */
    private String password;

    /**
     * app版本号
     */
    private String appVersion;

    /**
     * 设备 android or ios
     */
    private String channel;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 设备序列号
     */
    private String imei;

    /**
     * 第三方app登录请求的URL
     */
    private String loginUrl;

    /**
     * 第三方app提问时请求的URL
     */
    private String askUrl;

    /**
     * 第三方app登录时获取的token
     */
    private String loginToken;

    /**
     * 第三方登录的客户端ID
     */
    private String clientId;

    public ThirdAppAccount() {
    }

    public ThirdAppAccount(String strUserName, String strPwd, String password, String appVersion, String channel,
                           String nickName, String imei, String loginUrl, String askUrl, String loginToken, String clientId) {
        this.strUserName = strUserName;
        this.strPwd = strPwd;
        this.password = password;
        this.appVersion = appVersion;
        this.channel = channel;
        this.nickName = nickName;
        this.imei = imei;
        this.loginUrl = loginUrl;
        this.askUrl = askUrl;
        this.loginToken = loginToken;
        this.clientId = clientId;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrPwd() {
        return strPwd;
    }

    public void setStrPwd(String strPwd) {
        this.strPwd = strPwd;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getAskUrl() {
        return askUrl;
    }

    public void setAskUrl(String askUrl) {
        this.askUrl = askUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
