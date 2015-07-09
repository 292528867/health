package com.wonders.xlab.healthcloud.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.healthcloud.dto.ThirdAppAccount;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by wukai on 15/7/6.
 */
public final class ThirdAppConnUtils {
    private ThirdAppConnUtils(){
    }

    private static RestTemplate restTemplate = new RestTemplate();

    public static Map<String, ThirdAppAccount> appDatas = new HashMap<>();

    @PostConstruct
    private void init() {
        List messages=new ArrayList();
        messages.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        restTemplate.setMessageConverters(messages);
    }

    public static void initAppDatas(){
        appDatas = new HashMap<>();

        ThirdAppAccount ememedAccount = new ThirdAppAccount("15021470585",
                "111111", "", "2.6.1", "android", "",
                "865585020300848", "http://www.ememed.net:8004/normal/user/login",
                "http://www.ememed.net:8004/right/user/add_single_order","","");

        appDatas.put("ememed", ememedAccount);

        //血糖高管提问打URL
        String askurl = "http://doc.bskcare.com/bsk_doctor/pictureConsulting!createFreeProductInfo.action?mobileType={m1}";
        String source = "bsksugar-340";
        String loginurl = "http://facade.bskcare.com/nac_login.do?mobileType={m1}&sign={s2}&pwd={p3}&account={a4}";
        String pwd = "a691e69e72681a2e44f26c2549c9bcf4900210b5";
        String account = "15021470585";

        ThirdAppAccount basksugarAccount = new ThirdAppAccount();
        basksugarAccount.setChannel("android");
        basksugarAccount.setAskUrl(askurl);
        basksugarAccount.setLoginUrl(loginurl);
        basksugarAccount.setStrUserName(account);
        basksugarAccount.setPassword(pwd);
        appDatas.put("basksugar" , basksugarAccount);



    }

    /**
     * 登录第三方app获取个人信息或者token
     * @param appName
     */
    public static void loginApp(String appName){
        try {
            ThirdAppAccount appAccount = ThirdAppConnUtils.appDatas.get(appName);
            if(appAccount == null){
                return ;
            }
            //获取token,薏米医生
            if("ememed".equals(appName)){
                HttpHeaders headers = new HttpHeaders();
                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
                    add(MediaType.APPLICATION_FORM_URLENCODED);
                }};
                headers.setAccept(acceptableMediaTypes);

                Map<String, String> requestMap = new HashMap<>();
                requestMap.put("strusername", appAccount.getStrUserName());
                requestMap.put("appversion", appAccount.getAppVersion());
                requestMap.put("strpwd", appAccount.getStrPwd());
                requestMap.put("channel", appAccount.getChannel());
                requestMap.put("imei", appAccount.getImei());

                ResponseEntity result = requestEMChart(headers, HttpMethod.POST, requestMap, String.class, appAccount.getLoginUrl(), null);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> resultMap = objectMapper.readValue((String) result.getBody(), HashMap.class);
//                System.out.println("resultMap = " + resultMap);
                Map<String, Object> dataMap = (LinkedHashMap<String, Object>)resultMap.get("data");
                String token = (String)dataMap.get("TOKEN");
                String memberId = (String) dataMap.get("MEMBERID");
                appAccount.setClientId(memberId);
                appAccount.setLoginToken(token);

            } else if("basksugar".equals(appName)){
                //血糖高管登录
                //登录获取clientId
                Map<String, Object> uriVariables = new HashMap<>();
                uriVariables.put("m1", appAccount.getChannel());
                uriVariables.put("s2", UUID.randomUUID().toString().replaceAll("-",""));
                uriVariables.put("p3", appAccount.getPassword());
                uriVariables.put("a4", appAccount.getStrUserName());

                ResponseEntity result = getForEntity(appAccount.getLoginUrl(), String.class, uriVariables);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> resultMap = objectMapper.readValue((String) result.getBody(), HashMap.class);
                String clientId = (String)objectMapper.readValue(resultMap.get("data"), HashMap.class).get("cid");
//                System.out.println("resultMap = " + resultMap);
                appAccount.setClientId(clientId);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }



    public static ResponseEntity<?> requestEMChart(HttpHeaders headers, HttpMethod method, final Object body,  Class<?> classz, String url, Map<String, Object> uriVariables) {
        if (null == headers) {
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
//                add(MediaType.APPLICATION_JSON);
            }};
            headers = new HttpHeaders();
            headers.setAccept(acceptableMediaTypes);

        }

        ResponseEntity<?> result = restTemplate.exchange(
                url,
                method,
                //null == body ? 返回没有参数的请求entity : ( 参数是键值MAP型请求 ?  返回LinkedMultiValueMap类型的请求entity : 返回传入参数的请求entity)
                null == body ? new HttpEntity(headers) :
                        body instanceof Map ?
                                new HttpEntity(new LinkedMultiValueMap<String, Object>() {{
                                    setAll((Map<String, Object>) body);
                                }}, headers) :
                                new HttpEntity(body, headers),
                classz,
                uriVariables == null ? "" : uriVariables
        );

        Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
        return result;
    }

    public static ResponseEntity getForEntity(String url, Class<?> classz,  Map<String, Object> uriVariables){
        ResponseEntity result = restTemplate.getForEntity(url, classz, uriVariables);
        Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
        return result;
    }

    /**
     * 薏米医生 寻找免费的医生
     * @param appAccount
     * @return
     */
    public static List<String> findDoctorList(ThirdAppAccount appAccount) throws Exception{
        List<String> doctorList = new ArrayList<>();
        //薏米医生 找医生 search_filter_doctor
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
            add(MediaType.TEXT_HTML);
        }};
        headers.setAccept(acceptableMediaTypes);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("hospitalid", "");
        requestMap.put("memberid", appAccount.getClientId());
        requestMap.put("page", "1");
        requestMap.put("service_type", "1");
        //地区ID 上海45053 全国0
        requestMap.put("area_id", "0");
        requestMap.put("orderby", "2");
        requestMap.put("channel", appAccount.getChannel());
        requestMap.put("search_app_version", appAccount.getAppVersion());
        //科室
        requestMap.put("room", "");
        String doctorUrl = "http://www.ememed.net:8004/normal/user/search_filter_doctor";
        ResponseEntity result = ThirdAppConnUtils.requestEMChart(headers, HttpMethod.POST, requestMap, String.class, doctorUrl, null);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = objectMapper.readValue((String) result.getBody(), HashMap.class);

        for(LinkedHashMap paraMap : (ArrayList<LinkedHashMap<String, Object>>)resultMap.get("data")){
            if("1".equals(paraMap.get("ALLOWFREECONSULT"))){
                String doctorId = (String)paraMap.get("DOCTORID");
                doctorList.add(doctorId);
            }
        }
        return doctorList;
    }


}
