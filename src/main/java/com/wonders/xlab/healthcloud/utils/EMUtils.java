package com.wonders.xlab.healthcloud.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.healthcloud.dto.EMToken;
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Jeffrey on 15/7/4.
 */
@Component
@PropertySource("classpath:emchat.api/emchatApi.properties")
public class EMUtils {

    private static RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger(EMUtils.class);

    private static final String url = "http://a1.easemob.com/xlab/ugyufuy/{key}";

    private static HCCache<String, String> hcCache;

    @Value("${API_SERVER_HOST}")
    private String apiServerHost;

    @Value("${APPKEY}")
    private String appKey;

    @Value("${APP_CLIENT_ID}")
    private String appClientId;

    @Value("${APP_CLIENT_SECRET}")
    private String appClientSecret;

    @Autowired
    @Qualifier(value = "emCache")
    private static Cache emCache;

    static {
        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(new ArrayList<HttpMessageConverter<?>>() {{
            add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        }});
    }

    @PostConstruct
    private void init() {
        //配置缓存代理
        hcCache = new HCCacheProxy<>(emCache);
    }

    /**
     * 获取环信appToken放入到缓存
     *
     * @return
     */
    public String pushTokenToCache() {

        //添加请求头
        HttpHeaders header = new HttpHeaders() {{
            setAccept(new ArrayList<MediaType>() {{
                add(MediaType.APPLICATION_JSON);
            }});
        }};
        //配接获取环信token请求体
        String body = "{\"grant_type\":\"client_credentials\",\"client_id\":\"" +
                appClientId +
                "\",\"client_secret\":\"" +
                appClientSecret + "\"}";
        //发起HTTP请求
        ResponseEntity<EMToken> result = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, header),
                EMToken.class,
                new HashMap<String, Object>() {{
                    put("key", "token");
                }}
        );
        //请求成功
        if (HttpStatus.OK.equals(result.getStatusCode())) {
            String accessToken = result.getBody().getAccess_token();
            hcCache.addToCache("access_token", accessToken);
            logger.info("access_token={},applyDate＝{}", accessToken, DateUtils.covertToYYYYMMDDStr(new Date()));
            return accessToken;

        } else {
            throw new RuntimeException(result.getStatusCode().toString());
        }
    }

    /**
     * @param method 请求方式
     * @param path   向默认地址后拼接path路径 ：http://a1.easemob.com/xlab/ugyufuy/{path}
     * @param clazz  返回值body类型
     * @return
     */
    public ResponseEntity<?> requestEMChat(String method, String path, Class<?> clazz) {
        return requestEMChat(null, null, method, path, clazz);
    }

    /**
     * @param method 请求方式
     * @param body   请求体
     * @param path   向默认地址后拼接path路径 ：http://a1.easemob.com/xlab/ugyufuy/{path}
     * @param clazz  返回值body类型
     * @return
     */
    public ResponseEntity<?> requestEMChat(Object body, String method, String path, Class<?> clazz) {
        return requestEMChat(null, body, method, path, clazz);
    }

    /**
     * @param headers 请求头
     * @param method  请求方式
     * @param path    向默认地址后拼接path路径 ：http://a1.easemob.com/xlab/ugyufuy/{path}
     * @param clazz   返回值body类型
     * @return
     */
    public ResponseEntity<?> requestEMChat(HttpHeaders headers, String method, String path, Class<?> clazz) {
        return requestEMChat(headers, null, method, path, clazz);
    }

    /**
     * @param headers 请求头
     * @param method  请求方式
     * @param body    请求体
     * @param path    向默认地址后拼接path路径 ：http://a1.easemob.com/xlab/ugyufuy/{path}
     * @param clazz   返回值body类型
     * @return 请求返回ResponseEntity
     */
    public ResponseEntity<?> requestEMChat(HttpHeaders headers, final Object body, final String method, String path, Class<?> clazz) {

        //获取请求方式枚举
        HttpMethod httpMethod = EnumUtils.getEnum(HttpMethod.class, method.toUpperCase());
//----------------发布时，需取消以下注释－－－－－－－－－－－－－－－－
//        String token = hcCache.getFromCache("access_token");
//        //缓存Cache失效，重新请求放到缓存
//        if (StringUtils.isEmpty(token)) {
//            token = pushTokenToCache();
//        }

        //请求头为空调用默认请求头，不为空调用自定义请求头
        if (null == headers) {
            headers = new HttpHeaders();
            //定义请求头类型列表
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
                add(MediaType.APPLICATION_JSON);
            }};
            headers.setAccept(acceptableMediaTypes);

//----------------发布时，需取消以下注释，并注释headers.add("Authorization", "Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
//            headers.add("Authorization", "Bearer " + token);
            headers.add("Authorization", "Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
        }

        //添加需替换路径key 和 路径
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("key", path);

        //发起请求
        ResponseEntity<?> result = restTemplate.exchange(
                url,
                httpMethod,
                //null == body ? 返回没有参数的请求entity : ( 参数是键值MAP型请求 ?  返回LinkedMultiValueMap类型的请求entity : 返回传入参数的请求entity)
                null == body ? new HttpEntity(headers) :
                        body instanceof Map ?
                                //传入body为键值对创建键值对请求体
                                new HttpEntity<>(new LinkedMultiValueMap<String, Object>() {{
                                    setAll((Map) body);
                                }}, headers) :
                                new HttpEntity<>(body, headers),
                clazz,
                uriVariables
        );

        Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
        return result;
    }


    /**
     * 返回医生数量
     *
     * @return
     */
    public static int countDoctors() {
      /*  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String today = sdf.format(calendar.getTime()); //当前天格式：2015-05-05*/

        String today = DateFormatUtils.format(new Date(), "yyyy-MM-dd");//当前天格式：2015-05-05*/
        long currentHour = org.apache.commons.lang3.time.DateUtils.
                getFragmentInHours(new Date(), Calendar.DATE);  //当前小时

        //int currentHour = calendar.get(Calendar.HOUR_OF_DAY); //当前小时
        int dayForWeek = DateUtils.calculateTodayForWeek(); //当天是星期几
        List holidayList = DateUtils.getAlLHoliday();  //所有的节假日
        int doctorNumber1 = (int) (150 + Math.random() * 50);
        int doctorNumber2 = (int) (50 + Math.random() * 50);
        int doctorNumber3 = (int) (10 + Math.random() * 40);

        int value = 0;
        //首先判断是否是节假日
        if (holidayList.contains(today)) {
            if (currentHour >= 9 && currentHour < 18) {
                value = doctorNumber2;
            } else {
                value = doctorNumber3;
            }
        }
        if (dayForWeek <= 5) {//工作日
            if (currentHour >= 9 && currentHour < 18) {
                value = doctorNumber1;
            } else {
                value = doctorNumber3;
            }
        } else {//周末
            value = doctorNumber3;
        }
        return value;
    }

    /**
     * 返回超时时间
     *
     * @return
     */
    public static int getOvertime() {
        String today = DateFormatUtils.format(new Date(), "yyyy-MM-dd");//当前天格式：2015-05-05*/
        long currentHour = org.apache.commons.lang3.time.DateUtils.
                getFragmentInHours(new Date(), Calendar.DATE);  //当前小时
        int dayForWeek = DateUtils.calculateTodayForWeek(); //当天是星期几
        List holidayList = DateUtils.getAlLHoliday();  //所有的节假日

        int overtime = 0;
        if (holidayList.contains(today)) {
            overtime = 24 * 60;
        }
        if (dayForWeek <= 5) {//工作日
            if (currentHour >= 9 && currentHour < 18) overtime = 30;
            if (currentHour < 9 && currentHour >= 18) overtime = 12 * 60;
        } else {
            overtime = 12 * 60;
        }
        return overtime;
    }

    public boolean registerEmUser(final String username, final String password) {
        try {
            this.requestEMChat(new ObjectMapper().writeValueAsString(
                    new HashMap<String, String>() {{
                        put("username", username);
                        put("password", password);
                    }}
            ), "POST", "users", String.class);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
