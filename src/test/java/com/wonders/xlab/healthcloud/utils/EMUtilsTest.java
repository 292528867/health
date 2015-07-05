package com.wonders.xlab.healthcloud.utils;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffrey on 15/7/4.
 */
public class EMUtilsTest {

    private RestTemplate restTemplate = new RestTemplate();
    private static final String Authorization = "Authorization:Bearer YWMt0Ujc-CGAEeWGzdFWKNW-6QAAAU-I7R8lpklXS8QMECBLBFGdAGxHXWcckj";

    @Test
    public void testRequstEMChart() throws Exception {

        List<MediaType> mediaTypes = new ArrayList<MediaType>(){{
            add(MediaType.APPLICATION_JSON);
        }};
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type","application/json");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(mediaTypes);
        headers.add("Authorization","Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
        headers.add("restrict-access","true");
        String requestBody = "{" +
                "    \"target_type\" : \"users\"," +
                "    \"target\" : [\"Jeffery01\"]," +
                "    \"msg\" : {" +
                "        \"type\" : \"txt\"," +
                "        \"msg\" : \"hello from rest\" " +
                "        }," +
                "    \"from\" : \"Jeffery\", " +
                "    \"ext\" : { " +
                "        \"attr1\" : \"v1\"," +
                "        \"attr2\" : \"v2\"" +
                "    }    " +
                "}";

        final File file = new File("/Users/Jeffrey/Documents/portrait.jpg");
        Map<String, Object> request = new HashMap<String, Object>(){{
            put("file", file);
        }};
        ResponseEntity result = this.requstEMChart(headers, HttpMethod.POST, request, "chatfiles", String.class);
        System.out.println("result.getBody() = " + result.getBody());
    }

    @Test
    public void testRequstEMChatGet() throws Exception {
        List<MediaType> mediaTypes = new ArrayList<MediaType>(){{
            add(MediaType.APPLICATION_JSON);
        }};
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(mediaTypes);
        headers.add("Authorization","Bearer YWMtLTlNuCI6EeWD--V4NuV_CgAAAU-Nq-Qskj9BKL_nnRqWuGLEIW8lACwLMp4");

        ResponseEntity result = this.requstEMChart(headers, HttpMethod.GET, null, "chatgroups", String.class);
        System.out.println("result.getBody() = " + result.getBody());
    }

    public ResponseEntity<?> requstEMChart(HttpHeaders headers, HttpMethod method, Object body, String path, Class<?> classz) {

        if (headers == null) {
            headers = new HttpHeaders();
        }
        Map<String, Object> requestBody = new HashMap<>();
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.setAll((Map<String, Object>) body);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(param,headers);
//        if (StringUtils.isEmpty(body)) {
//            entity = new HttpEntity<>(headers);
//        } else {
//            entity = new HttpEntity<>(body, headers);
//        }

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("key", path);
        ResponseEntity<?> result = restTemplate.exchange(
//                "http://10.1.64.179:8080/xlab-healthcloud/user/{key}",
                "http://a1.easemob.com/xlab/ugyufuy/{key}",
                method,
                entity,
                classz,
                uriVariables
        );

        Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
        return result;
    }
}