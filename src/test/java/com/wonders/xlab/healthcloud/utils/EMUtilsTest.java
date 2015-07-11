package com.wonders.xlab.healthcloud.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.framework.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffrey on 15/7/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class EMUtilsTest {

    @Autowired
    private EMUtils emUtils;

    private static final String Authorization = "Authorization:Bearer Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw";

    @Test
    public void testRequstEMChart() throws Exception {

        List<MediaType> mediaTypes = new ArrayList<MediaType>(){{
            add(MediaType.APPLICATION_JSON);
        }};
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(mediaTypes);
        headers.add("Authorization","Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
//        headers.add("restrict-access","true");
//        String requestBody = "{" +
//                "    \"target_type\" : \"users\"," +
//                "    \"target\" : [\"Jeffery01\"]," +
//                "    \"msg\" : {" +
//                "        \"type\" : \"txt\"," +
//                "        \"msg\" : \"hello from rest\" " +
//                "        }," +
//                "    \"from\" : \"Jeffery\", " +
//                "    \"ext\" : { " +
//                "        \"attr1\" : \"v1\"," +
//                "        \"attr2\" : \"v2\"" +
//                "    }    " +
//                "}";

//        final File file = new File("/Users/Jeffrey/Documents/portrait.jpg");
        Map<String, Object> request = new HashMap<String, Object>(){{
            put("username", "2345678456");
            put("password", "17721013012");
        }};

        String body = new ObjectMapper().writeValueAsString(request);
        ResponseEntity result = emUtils.requestEMChat("POST", body, "users", String.class);
//        Map body = (Map) result.getBody();
//        System.out.println("result.getBody() = " + body.size());
        System.out.println("result.getBody() = " + result.getBody());
    }

    @Test
    public void testRequstEMChatGet() throws Exception {
        ResponseEntity result = emUtils.requestEMChat("get", null, "chatgroups", String.class);
        System.out.println("result.getBody() = " + result.getBody());
    }
}