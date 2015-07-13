//package com.wonders.xlab.healthcloud.utils;
//
//import com.wonders.xlab.healthcloud.dto.steward.LBSResult;
//import com.wonders.xlab.healthcloud.entity.steward.Steward;
//import org.apache.commons.lang3.EnumUtils;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class BaiduLBSUtil {
//	private static final String url = "http://api.map.baidu.com/geodata/v3/poi/create";
//	private static RestTemplate restTemplate;
//	//上传景点信息to百度LBS
//	@SuppressWarnings("unchecked")
//	public static String createPOI(Steward steward){
//		String url = "http://api.map.baidu.com/geodata/v3/poi/create";
//		Map params = new HashMap();
//		params.put("ak", ak);
//		params.put("geotable_id", geotable_id);
//		params.put("title", steward.getNickName());
//		params.put("address", steward.getLocation().getAddress());
//		//纬度
//		params.put("latitude", steward.getLocation().getLatitude());
//		//经度
//		params.put("longitude", steward.getLocation().getLongitude());
//		params.put("tags", "复活老上海");
//		params.put("coord_type", 1);
//
//		//每个管家对应的id
//		params.put("view_spot_id", steward.getId());
//
//		url += "?" + sb.toString();
//		return RestTemplateUtil.getLBSResult(url);
//	}
//
//	/**
//	 * 检索百度数据
//	 */
//	public static LBSResult nearbyJob(String coordinate, int pageIndex, long distance, int pageSize){
//		String url = "http://api.map.baidu.com/geosearch/v3/nearby";
//		StringBuffer sb = new StringBuffer();
//		sb.append("ak=").append(ak).append("&");
//		sb.append("geotable_id=").append(geotable_id).append("&");
//		sb.append("location=").append(coordinate).append("&");
//		sb.append("coord_type=").append(3).append("&");
//		sb.append("radius=").append(distance).append("&");
//		sb.append("tags=").append("管家").append("&");
//		sb.append("sortby=").append("distance:1").append("&");
//		sb.append("page_index=").append(pageIndex).append("&");
//		sb.append("page_size=").append(pageSize);
//		url += "?" + sb.toString();
//		return RestTemplateUtil.getLBSResult(url);
//
//	}
//	private static String ak = "GE3Pcd7SQ7sMSHy0RbZD1751";
//	private static String geotable_id = "104255";
//
//	public static void main(String[] args) {
//		Map params = new HashMap();
//		params.put("name", "around");
//		params.put("geotype", "1");
//		params.put("is_published", "1");
//		params.put("ak", ak);
//	}
//
//	/**
//	 *
//	 * @param headers 请求头
//	 * @param method 请求方式
//	 * @param body 请求体
//	 * @param path 向默认地址后拼接path路径 ：http://a1.easemob.com/xlab/ugyufuy/{path}
//	 * @param clazz 返回值body类型
//	 * @return 请求返回ResponseEntity
//	 */
//	public ResponseEntity<?> requestEMChat(HttpHeaders headers, final Object body, final String method, String path, Class<?> clazz) {
//
//		//获取请求方式枚举
//		HttpMethod httpMethod = EnumUtils.getEnum(HttpMethod.class, method.toUpperCase());
//		if (null == headers) {
//			headers = new HttpHeaders();
//			//定义请求头类型列表
//			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
//				add(MediaType.APPLICATION_JSON);
//			}};
//			headers.setAccept(acceptableMediaTypes);
//		}
//
//		//添加需替换路径key 和 路径
//		Map<String, Object> uriVariables = new HashMap<>();
//		uriVariables.put("key", path);
//		//发起请求
//		ResponseEntity<?> result = restTemplate.exchange(
//				url,
//				httpMethod,
//				null == body ? new HttpEntity(headers) :
//						body instanceof Map ?
//								//传入body为键值对创建键值对请求体
//								new HttpEntity(new LinkedMultiValueMap<String, Object>() {{
//									setAll((Map) body);
//								}}, headers) :
//								new HttpEntity(body, headers),
//				clazz,
//				uriVariables
//		);
//
//		Assert.isTrue(HttpStatus.OK.equals(result.getStatusCode()));
//		return result;
//	}
//}
