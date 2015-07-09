package com.wonders.xlab.healthcloud.utils;

import com.wonders.xlab.healthcloud.dto.EMToken;
import com.wonders.xlab.healthcloud.dto.emchat.ChatGroupsResponseBody;
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jeffrey on 15/7/4.
 */
@Component
@PropertySource("classpath:emcart.api/emchartApi.properties")
public class EMUtils {

    private RestTemplate restTemplate = new RestTemplate();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier(value = "emCache")
    private Cache emCache;

    private HCCache<String, String> hcCache;

    @PostConstruct
    private void init() {
        hcCache = new HCCacheProxy<>(emCache);
        List messages = new ArrayList();
        messages.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        restTemplate.setMessageConverters(messages);
    }

    public static final String url = "http://a1.easemob.com/xlab/ugyufuy/{key}";

    @Value("${API_SERVER_HOST}")
    public String apiServerHost;

    @Value("${APPKEY}")
    public String appKey;

    @Value("${APP_CLIENT_ID}")
    public String appClientId;

    @Value("${APP_CLIENT_SECRET}")
    public String appClientSecret;

    public String pushTokenToCache() {

        final List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON);
        }};
        //添加请求头
        HttpHeaders header = new HttpHeaders() {{
            setAccept(acceptableMediaTypes);
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
        if (HttpStatus.OK.equals(result.getStatusCode())) {
            String accessToken = result.getBody().getAccess_token();
            hcCache.addToCache("access_token", accessToken);
            logger.info("access_token={},applyDate＝{}", accessToken, DateUtils.covertToYYYYMMDDStr(new Date()));
            return accessToken;

        } else {
            throw new RuntimeException(result.getStatusCode().toString());
        }
    }

    public ResponseEntity<?> requestEMChart(HttpHeaders headers, HttpMethod method, final Object body, String path, Class<?> classz) {

//----------------发布时，需取消以下注释－－－－－－－－－－－－－－－－
//        String token = hcCache.getFromCache("access_token");
//        //缓存Cache失效，重新请求放到缓存
//        if (StringUtils.isEmpty(token)) {
//            token = pushTokenToCache();
//        }

        if (null == headers) {
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
                add(MediaType.APPLICATION_JSON);
            }};
            headers = new HttpHeaders();
            headers.setAccept(acceptableMediaTypes);

//----------------发布时，需取消以下注释，并注释headers.add("Authorization", "Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
//            headers.add("Authorization", "Bearer " + token);
            headers.add("Authorization", "Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
        }

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("key", path);


        ResponseEntity<?> result = restTemplate.exchange(
                url,
                method,
                //null == body ? 返回没有参数的请求entity : ( 参数是键值MAP型请求 ?  返回LinkedMultiValueMap类型的请求entity : 返回传入参数的请求entity)
                null == body ? new HttpEntity(headers) :
                        body instanceof Map ?
                                new HttpEntity(new LinkedMultiValueMap<String, Object>() {{
                                    setAll((Map) body);
                                }}, headers) :
                                new HttpEntity(body, headers),
                classz,
                uriVariables
        );

        Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
        return result;
    }

    public ResponseEntity<?> requestEMChart(HttpMethod method, Object body, String path, Class<?> classz) {

        return requestEMChart(null, method, body, path, classz);
    }

    public ResponseEntity<?> requestEMChart(HttpMethod method, String path, Class<?> classz) {
        return requestEMChart(null, method, null, path, classz);
    }

    public ResponseEntity<?> requestEMChart(HttpHeaders headers, HttpMethod method, String path, Class<?> classz) {
        return requestEMChart(headers, method, null, path, classz);
    }


    public String getApiServerHost() {
        return apiServerHost;
    }

    public void setApiServerHost(String apiServerHost) {
        this.apiServerHost = apiServerHost;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }

    public String getAppClientSecret() {
        return appClientSecret;
    }

    public void setAppClientSecret(String appClientSecret) {
        this.appClientSecret = appClientSecret;
    }


    /**
     * 返回医生数量
     *
     * @return
     */
    public static int getDoctorNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String today = sdf.format(calendar.getTime()); //当前天格式：2015-05-05
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY); //当前小时
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
            }else {
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Calendar calendar = Calendar.getInstance();
        String today = sdf.format(calendar.getTime()); //当前天格式：2015-05-05
        int currentHour = calendar.get(Calendar.HOUR); //当前小时
        int dayForWeek = DateUtils.calculateTodayForWeek(); //当天是星期几
        List holidayList = DateUtils.getAlLHoliday();  //所有的节假日

        if (holidayList.contains(today)) {
            return 24 * 60;
        }
        if (dayForWeek <= 5) {//工作日
            if (currentHour >= 9 && currentHour < 18) return 30;
            if (currentHour < 9 && currentHour >= 18) return 12 * 24;
        } else {
            return 12 * 60;
        }
        return 0;
    }
}
