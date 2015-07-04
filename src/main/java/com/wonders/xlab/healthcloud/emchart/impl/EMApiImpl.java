package com.wonders.xlab.healthcloud.emchart.impl;

import com.wonders.xlab.healthcloud.emchart.ApiProperties;
import com.wonders.xlab.healthcloud.emchart.EMApi;
import com.wonders.xlab.healthcloud.emchart.dto.Token;
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
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
public class EMApiImpl implements EMApi {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    @Qualifier(value = "emCache")
    private Cache emCache;

    private HCCache<String, String> hcCache;

    @PostConstruct
    private void init() {
        hcCache = new HCCacheProxy<>(emCache);
    }

    @Override
    public void pushTokenToCache() {

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        //设置请求头类型
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        HttpHeaders header = new HttpHeaders();
        //添加请求头
        header.setAccept(acceptableMediaTypes);
        //配接获取环信token请求体
        String body = "{\"grant_type\":\"client_credentials\",\"client_id\":\"" +
                apiProperties.APP_CLIENT_ID +
                "\",\"client_secret\":\"" +
                apiProperties.APP_CLIENT_SECRET + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(body, header);
        Map<String, Object> uriVariables = new HashMap<>();
        //设置动态路径替换值
        uriVariables.put("key", "token");

        ResponseEntity<Token> result = restTemplate.exchange(
                ApiProperties.url,
                HttpMethod.POST,
                entity,
                Token.class,
                uriVariables
        );

        if (HttpStatus.OK.equals(result.getStatusCode())) {
            hcCache.addToCache("access_token", result.getBody().getAccess_token());
        }else {
            new RuntimeException(result.getStatusCode().toString());
        }
    }


}
