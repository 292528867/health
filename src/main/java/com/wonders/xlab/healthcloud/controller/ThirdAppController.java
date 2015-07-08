package com.wonders.xlab.healthcloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.healthcloud.dto.QuestionRequestBody;
import com.wonders.xlab.healthcloud.dto.ThirdAppAccount;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.utils.ThirdAppConnUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 往第三方app后台发送请求controller
 * Created by wukai on 15/7/6.
 */
@RestController
@RequestMapping(value = "/third")
public class ThirdAppController {

    /**
     * 往第三方app后台发送请求
     *
     * @return
     */
    @RequestMapping(value = "ask", method = RequestMethod.POST)
    public ControllerResult sendMessages(@RequestBody QuestionRequestBody requestBody) throws Exception {
        Integer success = -1;
        for(String appName : requestBody.getAppName()){
            //每个平台尝试发送3次，一次成功以后不重复发送
            int i = 0;
            while(i < 3){
                String result = askQuestions(appName, requestBody);
                if("1".equals(result)){
                    success = 0;
                    break;
                }
            }
        }

        if(success == -1){
            return new ControllerResult().setRet_code(success).setRet_values("").setMessage("发送失败");
        } else {
            return new ControllerResult().setRet_code(success).setRet_values("").setMessage("发送成功");
        }
    }

    /**
     * 发送问题给第三方app后台服务器
     * @param appName
     * @param requestBody
     * @return
     * @throws Exception
     */
    public String askQuestions(String appName, QuestionRequestBody requestBody) throws Exception{
        String resultString = "";
        ThirdAppAccount appAccount = ThirdAppConnUtils.appDatas.get(appName);
        switch (appName){
            case "basksugar":{
                //血糖高管
                if(StringUtils.isEmpty(appAccount.getClientId())){
                    ThirdAppConnUtils.loginApp(appName);
                }
                HttpHeaders headers = new HttpHeaders();
                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
                    add(MediaType.TEXT_PLAIN);
                }};
                headers.setAccept(acceptableMediaTypes);
                Map<String, String> requestMap = new HashMap<>();
                //问题内容
                requestMap.put("freeProductInfo.topic", requestBody.getQuestion());
                //clientId 登录获取
                requestMap.put("freeProductInfo.clientId", appAccount.getClientId());
                requestMap.put("freeProductInfo.clientName", "");
                requestMap.put("freeProductInfo.pictureUrl", "");
                //sign
                requestMap.put("sign", UUID.randomUUID().toString().replaceAll("-", ""));

                Map<String, Object> uriVariables = new HashMap<>();
                uriVariables.put("m1", appAccount.getChannel());

                ResponseEntity responseEntity = ThirdAppConnUtils.requestEMChart(headers, HttpMethod.POST, requestMap, String.class, appAccount.getAskUrl(), uriVariables);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> resultMap = objectMapper.readValue((String) responseEntity.getBody(), HashMap.class);
                resultString = resultMap.get("code");
                break;
                }
            case "ememed": {
                //薏米医生
                if(StringUtils.isEmpty(appAccount.getLoginToken())){
                    ThirdAppConnUtils.loginApp(appName);
                }
                List<String> doctorList = ThirdAppConnUtils.findDoctorList(appAccount);
                long cursor = System.currentTimeMillis()%doctorList.size();
                //随即取一个医生发送
                String doctorId = doctorList.get((int)cursor);
                HttpHeaders headers = new HttpHeaders();
                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
                    add(MediaType.APPLICATION_FORM_URLENCODED);
                }};
                headers.setAccept(acceptableMediaTypes);
                Map<String, String> requestMap = new HashMap<>();
                requestMap.put("content", requestBody.getQuestion());
                requestMap.put("upload_file", "");
                requestMap.put("free", "1");
                requestMap.put("price", "0.0");
                requestMap.put("doctorid", doctorId);
                requestMap.put("memberid", appAccount.getClientId());
                requestMap.put("money_null", "1");
                requestMap.put("token", appAccount.getLoginToken());
                requestMap.put("appversion", appAccount.getAppVersion());
                requestMap.put("ordertype", "1");
                requestMap.put("channel", appAccount.getChannel());

                ResponseEntity responseEntity = ThirdAppConnUtils.requestEMChart(headers, HttpMethod.POST, requestMap, String.class, appAccount.getAskUrl(), null);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> resultMap = objectMapper.readValue((String) responseEntity.getBody(), HashMap.class);
                System.out.println("resultMap = " + resultMap);
                resultString = resultMap.get("success");

                break;
            }

            default : break;
        }

        return resultString;
    }



}
