package com.wonders.xlab.healthcloud.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wonders.xlab.healthcloud.dto.steward.LBSResult;
import com.wonders.xlab.healthcloud.entity.steward.Steward;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BaiduLBSUtil {

    private static RestTemplate restTemplate = new RestTemplate();
    private static String AK = "GhPZkLv9GhhN2otdfX5xasMH";
    private static String GEOTABLE_ID = "113464";

    //上传景点信息to百度LBS
    public static Object createPOI(Steward steward) throws JsonProcessingException, UnsupportedEncodingException {

        String url = "http://api.map.baidu.com/geodata/v3/poi/create";
        Map<String, Object> body = new HashMap<>();
        body.put("ak", AK);
        body.put("geotable_id", GEOTABLE_ID);
        body.put("title", URLEncoder.encode(steward.getNickName().toString(), "UTF-8"));
        body.put("address", URLEncoder.encode(steward.getLocation().getAddress(), "UTF-8"));
        //纬度
        body.put("latitude", steward.getLocation().getLatitude());
        //经度
        body.put("longitude", steward.getLocation().getLongitude());

        body.put("tags", URLEncoder.encode("健康云", "UTF-8"));
        body.put("coord_type", 1);

        //每个管家对应的id
        body.put("steward_id", steward.getId());

        return requestLBS(null, body, "POST", url, String.class).getBody();
    }

    /**
     * 检索百度数据
     */
    public static LBSResult nearbyJob(String coordinate, long distance) {
        String url = "http://api.map.baidu.com/geosearch/v3/nearby";
        StringBuffer sb = new StringBuffer();
        sb.append("ak=").append(AK).append("&");
        sb.append("geotable_id=").append(GEOTABLE_ID).append("&");
        sb.append("location=").append(coordinate).append("&");
        sb.append("coord_type=").append(3).append("&");
        sb.append("radius=").append(distance).append("&");
        sb.append("sortby=").append("distance:1");
        url += "?" + sb.toString();
        return getLBSResult(url);

    }

    /**
     * @param headers 请求头
     * @param method  请求方式
     * @param body    请求体
     * @param clazz   返回值body类型
     * @return 请求返回ResponseEntity
     */
    public static ResponseEntity<?> requestLBS(HttpHeaders headers, final Object body, final String method, final String url, Class<?> clazz) {

        //获取请求方式枚举
        HttpMethod httpMethod = EnumUtils.getEnum(HttpMethod.class, method.toUpperCase());
        if (null == headers) {
            headers = new HttpHeaders();
            //定义请求头类型列表
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
                add(MediaType.APPLICATION_JSON);
            }};
            headers.setAccept(acceptableMediaTypes);
        }
        //发起请求
        reInitMessageConverter(restTemplate);
        ResponseEntity<?> result = restTemplate.exchange(
                url,
                httpMethod,
                null == body ? new HttpEntity(headers) :
                        body instanceof Map ?
                                //传入body为键值对创建键值对请求体
                                new HttpEntity(new LinkedMultiValueMap<String, Object>() {{
                                    setAll((Map) body);
                                }}, headers) :
                                new HttpEntity(body, headers),
                clazz
        );

        Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
        return result;
    }


    private static void reInitMessageConverter(RestTemplate restTemplate) {

        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }

        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(converter);
    }

//    public static String getLBSResult(String reqUrl) {
//        String result = restTemplate.getForObject(reqUrl,String.class);
//        return result;
//    }

    public static LBSResult getLBSResult(String reqUrl) {
        LBSResult result = restTemplate.getForObject(reqUrl, LBSResult.class);
        return result;
    }
}
