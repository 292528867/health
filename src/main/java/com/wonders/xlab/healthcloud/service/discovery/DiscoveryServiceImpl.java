package com.wonders.xlab.healthcloud.service.discovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wonders.xlab.healthcloud.dto.discovery.HealthInfoDto;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoClickInfoRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoRepository;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.DiscoveryArticleRuleService;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.HealthInfoSample;
import com.wonders.xlab.healthcloud.service.drools.discovery.tag.DiscoveryTagRuleService;
import com.wonders.xlab.healthcloud.utils.DateUtils;

/**
 * 发现服务实现（使用drools规则引擎辅助计算）。
 * @author xu
 *
 */
@Service
@Transactional
public class DiscoveryServiceImpl implements DiscoveryService {
	@Autowired
	private DiscoveryArticleRuleService discoveryArticleRuleService;
	@Autowired
	private DiscoveryTagRuleService discoveryTagRuleService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HealthCategoryRepository healthCategoryRepository;
	@Autowired
	private HealthInfoRepository healthInfoRepository;
	@Autowired
	private HealthInfoClickInfoRepository healthInfoClickInfoRepository;
	
	@Override
	public List<HealthCategory> getRecommandTag(User user) {
		// 查询所有category
		List<HealthCategory> allCategories = this.healthCategoryRepository.findAll();
		// 获取用户关联的分类
		Set<HealthCategory> userCategories = user.getHcs();
				
		// 规则计算
		Long[] ids = this.discoveryTagRuleService.pushTags(user, allCategories, userCategories);
		List<HealthCategory> healthCategories = this.healthCategoryRepository.findAll(Arrays.asList(ids));
		List<HealthCategory> healthCategories_order = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			for (HealthCategory hc : healthCategories) {
				if (hc.getId() == ids[i]) {
					healthCategories_order.add(hc);
					break;
				}
			}
		}
		
		return healthCategories_order;
	}
	
	@Override
	public List<HealthCategory> getRecommandTag(User user,
			HealthCategory... categories) {
		// 查询所有category
		List<HealthCategory> allCategories = this.healthCategoryRepository.findAll();
		// 获取用户关联的分类
		Set<HealthCategory> userCategories = new HashSet<>(); // 替换用户设定的关心分类
		for (HealthCategory hc : categories) 
			userCategories.add(hc);
		
		// 规则计算
		Long[] ids = this.discoveryTagRuleService.pushTags(user, allCategories, userCategories);
		List<HealthCategory> healthCategories = this.healthCategoryRepository.findAll(Arrays.asList(ids));
		List<HealthCategory> healthCategories_order = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			for (HealthCategory hc : healthCategories) {
				if (hc.getId() == ids[i]) {
					healthCategories_order.add(hc);
					break;
				}
			}
		}
		return healthCategories_order;
	}
	
	@Override
	public List<HealthInfoDto> getRecommandArticles(User user) {
		List<HealthInfo> healthInfos_1 = new ArrayList<>();
		// 用户关心分类下的健康信息文章
		User user_all = this.userRepository.queryUserHealthInfo(user.getId());
		for (HealthCategory healthCategory : user_all.getHcs()) 
			healthInfos_1.addAll(healthCategory.getHins());			
		Map<Long, Long> idMaps = discoveryArticleRuleService.pushArticles(user.getId(), healthInfos_1, 4);
		
		List<HealthInfo> healthInfos = this.healthInfoRepository.findAll(idMaps.keySet());
		List<HealthInfoDto> healthInfoDtoes = new ArrayList<HealthInfoDto>();
		for (HealthInfo h : healthInfos) {
			HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(h); 
			dto.setClickCount(idMaps.get(h.getId()));
			healthInfoDtoes.add(dto);
		}
		
		// 查出1级关联，无关级关联的文章各1篇
		// 查询所有category
		List<String> ids_1_strs = new ArrayList<>();
		List<String> ids_n_strs = new ArrayList<>();
		for (HealthCategory hc : user.getHcs()) {
			ids_1_strs.addAll(Arrays.asList(StringUtils.split(hc.getFirstRelatedIds(), ",")));
			ids_n_strs.addAll(Arrays.asList(StringUtils.split(hc.getOtherRelatedIds(), ",")));
		}
		List<Long> ids_1_long = new ArrayList<>();
		List<Long> ids_n_long = new ArrayList<>();
		for (String str : ids_1_strs) 
			ids_1_long.add(Long.parseLong(str));
		for (String str : ids_n_strs) 
			ids_n_long.add(Long.parseLong(str));
		List<HealthInfo> ids_1_long_infos = this.healthInfoRepository.findHealthCategoryIds(ids_1_long.toArray(new Long[0]));
		List<HealthInfo> ids_n_long_infos = this.healthInfoRepository.findHealthCategoryIds(ids_n_long.toArray(new Long[0]));
		
		System.out.println("ids_1_long.size() - >" + ids_1_long_infos.size());
		System.out.println("ids_n_long.size() - >" + ids_n_long_infos.size());
		
		Map<Long, Long> idMaps_2_1 = discoveryArticleRuleService.pushArticles(user.getId(), ids_1_long_infos, 1);
		Map<Long, Long> idMaps_2_3 = discoveryArticleRuleService.pushArticles(user.getId(), ids_n_long_infos, 1);
		
		System.out.println("关联ids，1级别关联：" + idMaps_2_1);
		System.out.println("关联ids，无关级别关联：" + idMaps_2_3);
		
		
		List<HealthInfo> healthInfos_2_1_list = this.healthInfoRepository.findAll(idMaps_2_1.keySet());
		List<HealthInfo> healthInfos_2_3_list = this.healthInfoRepository.findAll(idMaps_2_3.keySet());
		
		List<HealthInfoDto> healthInfoDtoes2 = new ArrayList<HealthInfoDto>();
		for (HealthInfo h : healthInfos_2_1_list) {
			HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(h); 
			dto.setClickCount(idMaps_2_1.get(h.getId()));
			healthInfoDtoes2.add(dto);
		}
		for (HealthInfo h : healthInfos_2_3_list) {
			HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(h); 
			dto.setClickCount(idMaps_2_3.get(h.getId()));
			healthInfoDtoes2.add(dto);
		}
		
		healthInfoDtoes.addAll(healthInfoDtoes2);
		
//		if (healthInfoDtoes2.size() == 1) {
//			healthInfoDtoes.remove(5);
//			healthInfoDtoes.addAll(healthInfoDtoes2);
//		}
//		if (healthInfoDtoes2.size() == 2) {
//			healthInfoDtoes.remove(5);
//			healthInfoDtoes.remove(4);
//			healthInfoDtoes.addAll(healthInfoDtoes2);
//		}
		
		return healthInfoDtoes;
	}
	
	@Override
	public List<HealthInfoDto> getTagInfos(HealthCategory healthCategory, User user) {
		// 获取某个标签所有信息
		List<HealthInfo> healthInfos = this.healthInfoRepository.findByHealthCategoryId(healthCategory.getId());
		// 获取其相关点击数
		List<Object> clickInfos = this.healthInfoClickInfoRepository.healthInfoTotalClickCountWithCategoryId(healthCategory.getId());
		Map<Long, Long> actual_clickInfos_map = new HashMap<>();
		if (clickInfos != null) {
			for (Object record : clickInfos) {
				Object[] record_values = (Object[]) record;
				Long infoId = (Long) record_values[0];
				Long clickCount = (Long) record_values[1];
				actual_clickInfos_map.put(infoId, clickCount);
			}
		}
		
		// 构造样本数据，计算模拟点击数
		List<HealthInfoSample> healthInfoSamples = new ArrayList<>();
		for (HealthInfo healthInfo : healthInfos) {
			HealthInfoSample sample = new HealthInfoSample(
				user.getId(), 
				healthInfo.getCreatedDate(), 
				healthInfo.getId(), 
				healthInfo.getTitle(), 
				actual_clickInfos_map.get(healthInfo.getId()) == null ? 0 : actual_clickInfos_map.get(healthInfo.getId())
			);
			healthInfoSamples.add(sample);
		}
		// 规则计算虚拟点击数目
		Map<Long, Long> virtual_clickInfos_maps = this.discoveryArticleRuleService.calcuClickCount(20, 0.1, healthInfoSamples);
		// 整合dto输出
		List<HealthInfoDto> dtos = new ArrayList<>();
		for (HealthInfo healthInfo : healthInfos) {
			HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(healthInfo);
			dto.setClickCount(virtual_clickInfos_maps.get(healthInfo.getId()));
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public HealthInfoClickInfo clickHealthInfo(User user, HealthInfo healthInfo) {
		HealthInfoClickInfo clickInfo = this.healthInfoClickInfoRepository.findByUserIdAndClickDateAndHealthInfoId(
				user.getId(), DateUtils.covertToYYYYMMDD(new Date()), healthInfo.getId());
		if (clickInfo == null) {
			clickInfo = new HealthInfoClickInfo();
			clickInfo.setClickCount(0L);
			clickInfo.setClickDate(DateUtils.covertToYYYYMMDD(new Date()));
			clickInfo.setHealthInfo(healthInfo);
			clickInfo.setUser(user);
		}
		clickInfo.setClickCount(clickInfo.getClickCount() + 1);
		this.healthInfoClickInfoRepository.save(clickInfo);
		return clickInfo;
	}
	
	@Override
	public HealthInfoDto detailHealthInfo(HealthInfo healthInfo) {
		// 获取点击数
		Long clickCount = this.healthInfoClickInfoRepository.healthInfoTotalClickCount(healthInfo.getId());
		
		// 规则计算点击数
		HealthInfoSample sample = new HealthInfoSample(
			0L, 
			healthInfo.getCreatedDate(), 
			healthInfo.getId(), 
			healthInfo.getTitle(), 
			clickCount == null ? 0 : clickCount);
		List<HealthInfoSample> sampleList = new ArrayList<>();
		sampleList.add(sample);
		Map<Long, Long> click_map = this.discoveryArticleRuleService.calcuClickCount(20, 0.1, sampleList);
		
		HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(healthInfo);
		dto.setClickCount(click_map.get(healthInfo.getId()));
		
		return dto;
	}
	
	
}
