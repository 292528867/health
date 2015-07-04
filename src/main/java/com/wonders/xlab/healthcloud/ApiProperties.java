package com.wonders.xlab.healthcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Jeffrey on 15/7/4.
 */
@Configuration
@PropertySource("classpath:emcart.api/emchartApi.properties")
public class ApiProperties {

    public static final String url = "http://a1.easemob.com/xlab/ugyufuy/{key}";

    @Value("${API_SERVER_HOST}")
    public String API_SERVER_HOST;

    @Value("${APPKEY}")
    public String APPKEY;

    @Value("${APP_CLIENT_ID}")
    public String APP_CLIENT_ID;

    @Value("${APP_CLIENT_SECRET}")
    public String APP_CLIENT_SECRET;

    public static String getUrl() {
        return url;
    }

    public String getAPI_SERVER_HOST() {
        return API_SERVER_HOST;
    }

    public void setAPI_SERVER_HOST(String API_SERVER_HOST) {
        this.API_SERVER_HOST = API_SERVER_HOST;
    }

    public String getAPPKEY() {
        return APPKEY;
    }

    public void setAPPKEY(String APPKEY) {
        this.APPKEY = APPKEY;
    }

    public String getAPP_CLIENT_ID() {
        return APP_CLIENT_ID;
    }

    public void setAPP_CLIENT_ID(String APP_CLIENT_ID) {
        this.APP_CLIENT_ID = APP_CLIENT_ID;
    }

    public String getAPP_CLIENT_SECRET() {
        return APP_CLIENT_SECRET;
    }

    public void setAPP_CLIENT_SECRET(String APP_CLIENT_SECRET) {
        this.APP_CLIENT_SECRET = APP_CLIENT_SECRET;
    }
}
