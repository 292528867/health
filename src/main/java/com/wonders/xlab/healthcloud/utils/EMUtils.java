package com.wonders.xlab.healthcloud.utils;

import com.wonders.xlab.healthcloud.dto.EMToken;
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffrey on 15/7/4.
 */
@Component
@PropertySource("classpath:emcart.api/emchartApi.properties")
public class EMUtils {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    @Qualifier(value = "emCache")
    private Cache emCache;

    private HCCache<String, String> hcCache;

    @PostConstruct
    private void init() {
        hcCache = new HCCacheProxy<>(emCache);
    }

    public static final String url = "http://a1.easemob.com/xlab/ugyufuy/{key}";

    @Value("${API_SERVER_HOST}")
    public String API_SERVER_HOST;

    @Value("${APPKEY}")
    public String APPKEY;

    @Value("${APP_CLIENT_ID}")
    public String APP_CLIENT_ID;

    @Value("${APP_CLIENT_SECRET}")
    public String APP_CLIENT_SECRET;

    public void pushTokenToCache() {

        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON);
        }};
        HttpHeaders header = new HttpHeaders();
        //添加请求头
        header.setAccept(acceptableMediaTypes);
        //配接获取环信token请求体
        String body = "{\"grant_type\":\"client_credentials\",\"client_id\":\"" +
                APP_CLIENT_ID +
                "\",\"client_secret\":\"" +
                APP_CLIENT_SECRET + "\"}";

        ResponseEntity result = requstEMChart(header, HttpMethod.POST, body, "token", EMToken.class);

        if (HttpStatus.OK.equals(result.getStatusCode())) {
            hcCache.addToCache("access_token", ((EMToken) result.getBody()).getAccess_token());
        } else {
            throw new RuntimeException(result.getStatusCode().toString());
        }
    }

    public ResponseEntity<?> requstEMChart(HttpHeaders headers, HttpMethod method, String body, String path, Class<?> classz) {

        HttpEntity<String> entity = null;
        if (StringUtils.isEmpty(body)) {
            entity = new HttpEntity<>(headers);
        } else {
            entity = new HttpEntity<>(body, headers);
        }

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("key", path);
        ResponseEntity<?> result = restTemplate.exchange(
                url,
                method,
                entity,
                classz,
                uriVariables
        );

        Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
        return result;
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
