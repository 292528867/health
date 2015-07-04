package com.wonders.xlab.healthcloud.utils;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeffrey on 15/7/4.
 */
public class EMUtilsTest {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testRequstEMChart() throws Exception {

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
                "http://10.1.64.179:8080/xlab-healthcloud/user/1",
                method,
                entity,
                classz,
                uriVariables
        );

        Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
        return result;
    }
}