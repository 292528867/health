package com.wonders.xlab.healthcloud.service.discovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.xlab.healthcloud.dto.discovery.HealthInfoDto;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoDiscovery;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoUserClickInfo;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoClickInfoRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoDiscoveryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoUserClickInfoRepository;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.DiscoveryArticleRuleService;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.HealthInfoClickSample;
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
	@Autowired
	private HealthInfoUserClickInfoRepository healthInfoUserClickInfoRepository;
	@Autowired
	private HealthInfoDiscoveryRepository healthInfoDiscoveryRepository;
	
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
		// 1、查出首页配置给发现的文章1篇（目前没有）
		// TODO：
		
		// 2、查出用户关心分类下文章4篇
		List<HealthInfo> healthInfos_2 = new ArrayList<>();
		User user_all = this.userRepository.queryUserHealthInfo(user.getId());
		for (HealthCategory healthCategory : user_all.getHcs()) 
			healthInfos_2.addAll(healthCategory.getHins());
		List<HealthInfoSample> healthInfoSamples_2 = new ArrayList<>();
		for (HealthInfo healthInfo : healthInfos_2) {
			HealthInfoSample healthInfoSample = new HealthInfoSample(
				user.getId(), user.getCreatedDate(), healthInfo.getId(), 
				healthInfo.getTitle(), healthInfo.getHealthInfoClickInfo().getVirtualClickCount(), healthInfo);
			healthInfoSamples_2.add(healthInfoSample);
		}
		List<HealthInfo> healthInfos_out_2 = discoveryArticleRuleService.pushArticles(user.getId(), healthInfoSamples_2, 4);
		
		// 3、查出1级关联分类文章1篇
		List<String> ids_1_strs = new ArrayList<>();
		for (HealthCategory hc : user.getHcs()) {
			if (StringUtils.isNotEmpty(hc.getFirstRelatedIds())) 
				ids_1_strs.addAll(Arrays.asList(StringUtils.split(hc.getFirstRelatedIds(), ",")));	
		}
		List<Long> ids_1_long = new ArrayList<>();
		for (String str : ids_1_strs) 
			ids_1_long.add(Long.parseLong(str));
		List<HealthInfo> healthInfos_3 = new ArrayList<>();
		if (ids_1_long.size() > 0)
			healthInfos_3.addAll(this.healthInfoRepository.findHealthCategoryIdsWithClickInfo(ids_1_long.toArray(new Long[0])));
		List<HealthInfoSample> healthInfoSamples_3 = new ArrayList<>();
		for (HealthInfo healthInfo : healthInfos_3) {
			HealthInfoSample healthInfoSample = new HealthInfoSample(
				user.getId(), user.getCreatedDate(), healthInfo.getId(), 
				healthInfo.getTitle(), healthInfo.getHealthInfoClickInfo().getVirtualClickCount(), healthInfo);
			healthInfoSamples_3.add(healthInfoSample);
		}
		List<HealthInfo> healthInfos_out_3 = discoveryArticleRuleService.pushArticles(user.getId(), healthInfoSamples_3, 1);
		
		
		// 4、查出无级关联分类文章1篇
		List<String> ids_n_strs = new ArrayList<>();
		for (HealthCategory hc : user.getHcs()) {
			if (StringUtils.isNotEmpty(hc.getFirstRelatedIds())) 
				ids_n_strs.addAll(Arrays.asList(StringUtils.split(hc.getFirstRelatedIds(), ",")));
		}
		List<Long> ids_n_long = new ArrayList<>();
		for (String str : ids_n_strs) 
			ids_n_long.add(Long.parseLong(str));
		List<HealthInfo> healthInfos_4 = new ArrayList<>();
		if (healthInfos_4.size() > 0)
			healthInfos_4.addAll(this.healthInfoRepository.findHealthCategoryIdsWithClickInfo(ids_n_long.toArray(new Long[0])));
		List<HealthInfoSample> healthInfoSamples_4 = new ArrayList<>();
		for (HealthInfo healthInfo : healthInfos_4) {
			HealthInfoSample healthInfoSample = new HealthInfoSample(
				user.getId(), user.getCreatedDate(), healthInfo.getId(), 
				healthInfo.getTitle(), healthInfo.getHealthInfoClickInfo().getVirtualClickCount(), healthInfo);
			healthInfoSamples_4.add(healthInfoSample);
		}
		List<HealthInfo> healthInfos_out_4 = discoveryArticleRuleService.pushArticles(user.getId(), healthInfoSamples_4, 1);
		
		// End，更新到数据库中
		List<HealthInfo> allHealthInfos = new ArrayList<>();
		allHealthInfos.addAll(healthInfos_out_2);
		allHealthInfos.addAll(healthInfos_out_3);
		allHealthInfos.addAll(healthInfos_out_4);
		List<HealthInfoDto> healthInfoDtoes = new ArrayList<>();
		for (HealthInfo healthInfo : healthInfos_out_2) {
			HealthInfoDto healthInfoDto = new HealthInfoDto();
			healthInfoDto = new HealthInfoDto().toNewHealthInfoDto(healthInfo);
			healthInfoDto.setClickCount(healthInfo.getHealthInfoClickInfo().getVirtualClickCount());
			healthInfoDtoes.add(healthInfoDto);
		}
		
		return healthInfoDtoes;
	}
	
	@Override
	public Page<HealthInfoDto> getTagInfos(HealthCategory healthCategory, User user, Pageable pageable) {
		// 获取推荐的文章
		HealthInfoDiscovery healthInfoDiscovery = this.healthInfoDiscoveryRepository.findByUserIdAndDiscoveryDate(
				user.getId(), DateUtils.covertToYYYYMMDD(new Date()));
		List<Long> ids_long_list = new ArrayList<>();
		if (healthInfoDiscovery != null) {
			String[] ids_str_array = StringUtils.split(healthInfoDiscovery.getDiscoveryHealthInfoIds(), ",");
			for (String id : ids_str_array) 
				ids_long_list.add(Long.parseLong(id));
		}
		
		
		Page<HealthInfo> page = null;
		if (ids_long_list.size() == 0) {
			page = this.healthInfoRepository.pageablefindByCategoryId(
					healthCategory.getId(), 
					pageable);
		} else {
			page = this.healthInfoRepository.pageablefindByCategoryIdWithOutIds(
					healthCategory.getId(), 
					ids_long_list, 
					pageable);
		}
				
				
				
		// 重置分组内容
		List<HealthInfoDto> dtoes = new ArrayList<>();
		for (HealthInfo healthInfo : page.getContent()) {
			HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(healthInfo);
			dto.setClickCount(healthInfo.getHealthInfoClickInfo().getVirtualClickCount());
			dtoes.add(dto);
		}
		PageImpl<HealthInfoDto> newpage = new PageImpl<>(dtoes, pageable, page.getTotalElements());
		return newpage;
	}
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public HealthInfoClickInfo clickHealthInfo(User user, HealthInfo healthInfo) {
		HealthInfoClickInfo healthInfoClickInfo = this.healthInfoClickInfoRepository.findByHealthInfoId(healthInfo.getId());
		if (healthInfoClickInfo == null) 
			throw new RuntimeException("HealthInfoClickInfo 应该和 HealthInfo一同创建！");
		HealthInfoUserClickInfo healthInfoUserClickInfo = this.healthInfoUserClickInfoRepository.findByUserIdAndClickDateAndHealthInfoId(
				user.getId(), DateUtils.covertToYYYYMMDD(new Date()), healthInfo.getId());
		if (healthInfoUserClickInfo == null) { // 用户点击为空，则创建
			healthInfoUserClickInfo = new HealthInfoUserClickInfo();
			healthInfoUserClickInfo.setClickCount(0L);
			healthInfoUserClickInfo.setVirtualClickCount(0L);
			healthInfoUserClickInfo.setClickDate(DateUtils.covertToYYYYMMDD(new Date()));
			healthInfoUserClickInfo.setHealthInfo(healthInfo);
			healthInfoUserClickInfo.setUser(user);
			this.healthInfoUserClickInfoRepository.save(healthInfoUserClickInfo);
		}
		// 用户实际点击数+1（可能丢失更新）
		healthInfoUserClickInfo.setClickCount(healthInfoUserClickInfo.getClickCount() + 1);
		// 文章实际点击数+1（可能丢失更新）
		healthInfoClickInfo.setClickCount(healthInfoClickInfo.getClickCount() + 1);
		
		// 规则计算点击数
		HealthInfoClickSample healthInfoClickSample = new HealthInfoClickSample(
			healthInfo.getId(), healthInfo.getCreatedDate(), healthInfoUserClickInfo.getClickCount(), healthInfoClickInfo.getClickCountA()
		);
		Map<Long, Long> click_map = this.discoveryArticleRuleService.calcuClickCount(0.1, healthInfoClickSample); // X=0.1
		long virtualHealthInfoClickCount = click_map.get(healthInfo.getId());
		
		// 更新用户虚拟点击数（可能丢失更新）
		healthInfoUserClickInfo.setVirtualClickCount(virtualHealthInfoClickCount);
		// 累加文章虚拟点击数（可能丢失更新）
		healthInfoClickInfo.setVirtualClickCount(healthInfoClickInfo.getVirtualClickCount() + virtualHealthInfoClickCount);
		
		return healthInfoClickInfo;
	}
	
	@Override
	public HealthInfoDto detailHealthInfo(HealthInfo healthInfo) {
		// 直接从healthInfoClickInfo中获取虚拟点击数
		// 如果要实时计算，则使用规则重新计算罗，参照clickHealthInfo方法
		HealthInfoClickInfo healthInfoClickInfo = this.healthInfoClickInfoRepository.findByHealthInfoId(healthInfo.getId());
		long clickCount = healthInfoClickInfo.getVirtualClickCount();
		
		HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(healthInfo);
		dto.setClickCount(clickCount);
		
		return dto;
	}
	
	
}
