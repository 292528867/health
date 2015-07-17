package com.wonders.xlab.healthcloud.service.discovery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wonders.xlab.healthcloud.dto.discovery.HealthInfoDto;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategoryDiscovery;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoDiscovery;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryDiscoveryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoClickInfoRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoDiscoveryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoUserClickInfoRepository;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.DiscoveryArticleRuleService;
import com.wonders.xlab.healthcloud.utils.DateUtils;

/**
 * 发现服务代理（缓存，数据库保存）。
 * @author xu
 *
 */
@Service
public class DiscoveryServiceProxy implements DiscoveryService {
	
	@Autowired
	@Qualifier("discoveryServiceImpl")
	private DiscoveryService discoveryService;
	@Autowired
	private DiscoveryArticleRuleService discoveryArticleRuleService;
	
	@Autowired
	private HealthCategoryDiscoveryRepository healthCategoryDiscoveryRepository;
	@Autowired
	private HealthInfoDiscoveryRepository healthInfoDiscoveryRepository;
	@Autowired
	private HealthCategoryRepository healthCategoryRepository;
	@Autowired
	private HealthInfoRepository healthInfoRepository;
	@Autowired
	private HealthInfoClickInfoRepository healthInfoClickInfoRepository;
	@Autowired
	private HealthInfoUserClickInfoRepository healthInfoUserClickInfoRepository;
	
//	@Autowired
//	@Qualifier("discoveryCache")
//	private Cache discoveryCache;
//	/** 缓存代理（key="{用户id}_{yyyy-MM-dd}_articles）"，value={HealthInfoDayDiscovery对象}） */
//	private HCCache<String, Object> proxyCache;
//	
//	@PostConstruct
//	private void initBean() {
//		proxyCache = new HCCacheProxy<String, Object>(discoveryCache);
//	}
	
	
	private void setDtoIsClicked(User user, List<HealthInfoDto> dtoes) {
		Map<Long, Long> userClicked = new HashMap<>();
		List<Object> allClickCountList = this.healthInfoUserClickInfoRepository.healthInfoTotalClickCountWithUserId(user.getId());
		for (Object record : allClickCountList) {
			Object[] record_values = (Object[]) record;
			userClicked.put((Long) record_values[0], (Long) record_values[1]);
		}
		for (HealthInfoDto dto : dtoes) {
			if (userClicked.get(dto.getId()) != null) 
				dto.setClicked(true);
			else
				dto.setClicked(false);
		}
	}
	
	private String toHealthCategoryIdStrs(List<HealthCategory> healthCategoryList) {
		List<String> ids_str_array = new ArrayList<>();
		for (HealthCategory healthCategory : healthCategoryList) {
			String id = String.valueOf(healthCategory.getId());
			ids_str_array.add(id);
		}
		return StringUtils.join(ids_str_array, ",");
	}
	
	private String toHealthInfoDtoIdStrs(List<HealthInfoDto> healthInfoDtoList) {
		List<String> ids_str_array = new ArrayList<>();
		for (HealthInfoDto healthInfoDto : healthInfoDtoList) {
			String id = String.valueOf(healthInfoDto.getId());
			ids_str_array.add(id);
		}
		return StringUtils.join(ids_str_array, ",");
	}
	
	private List<HealthCategory> getHealthCategoryListFromStrids(String ids) {
		String[] ids_str_array = StringUtils.split(ids, ",");
		List<Long> ids_long_list = new ArrayList<>();
		for (String id : ids_str_array) 
			ids_long_list.add(Long.parseLong(id));
		
		List<HealthCategory> healthCategories = this.healthCategoryRepository.findAll(ids_long_list);
		List<HealthCategory> healthCategories_order = new ArrayList<>();
		for (int i = 0; i < ids_long_list.size(); i++) {
			for (HealthCategory hc : healthCategories) {
				if (hc.getId() == ids_long_list.get(i)) {
					healthCategories_order.add(hc);
					break;
				}
			}
		}
		return healthCategories_order;
	}
	
	private List<HealthInfoDto> getHealthInfoDtoListFromStrids(String ids) {
		String[] ids_str_array = StringUtils.split(ids, ",");
		List<Long> ids_long_list = new ArrayList<>();
		for (String id : ids_str_array) 
			ids_long_list.add(Long.parseLong(id));
		
		List<HealthInfo> healthInfos = this.healthInfoRepository.findByHealthInfoIdsWithClickInfo(ids_long_list.toArray(new Long[0]));
		List<HealthInfoDto> dtoes = new ArrayList<>();
		for (HealthInfo healthInfo : healthInfos) {
			HealthInfoDto healthInfoDto = new HealthInfoDto().toSimpleHealthInfoDto(healthInfo);
			healthInfoDto.setClickCount(healthInfo.getHealthInfoClickInfo().getVirtualClickCount());
			dtoes.add(healthInfoDto);
			
		}
		return dtoes;
	}
	
	@Override
	public List<HealthCategory> getRecommandTag(User user) {
		// 开始代理
		List<HealthCategory> healthCategoryList = new ArrayList<>();

		// 1、从缓存中获取
		// 2、缓存从数据苦里获取，并放入缓存
		// 3、数据库里没有，调用被代理的类，返回的结果放入数据库，并放入缓存，并返回
		
		// 暂时只用数据库保存，不加缓存保存，便于修改，如果以后加入了ehcache JMX管理后再改回来
		Date date = new Date();
//		String key = user.getId() + "_" + DateUtils.covertToYYYYMMDDStr(date) + "_" + "tags";
		HealthCategoryDiscovery healthCategoryDiscovery = this.healthCategoryDiscoveryRepository.findByUserIdAndDiscoveryDate(
				user.getId(), DateUtils.covertToYYYYMMDD(date));
		if (healthCategoryDiscovery == null) {
			healthCategoryList.addAll(this.discoveryService.getRecommandTag(user));
			if (healthCategoryList.size() > 0) { // 有推荐才保存数据库
				healthCategoryDiscovery = new HealthCategoryDiscovery();
				healthCategoryDiscovery.setDiscoveryDate(DateUtils.covertToYYYYMMDD(date));
				healthCategoryDiscovery.setDiscoveryHealthCategoryIds(toHealthCategoryIdStrs(healthCategoryList));
				healthCategoryDiscovery.setUser(user);
				this.healthCategoryDiscoveryRepository.save(healthCategoryDiscovery);
			}
			
		} else {
			healthCategoryList.addAll(getHealthCategoryListFromStrids(healthCategoryDiscovery.getDiscoveryHealthCategoryIds()));
		}
		
		return healthCategoryList;
	}
	
	@Override
	public List<HealthCategory> getRecommandTag(User user,
			HealthCategory... categories) {
		// 不用缓存，直接返回
		return this.discoveryService.getRecommandTag(user, categories);
	}
	
	@Override
	public Page<HealthInfoDto> getTagInfos(HealthCategory healthCategory, User user, Pageable pageable) {
		// 计算是否点击此用户是否点击过
		Page<HealthInfoDto> page = this.discoveryService.getTagInfos(healthCategory, user, pageable);
		setDtoIsClicked(user, page.getContent());
		return page;
	}
	
	@Override
	public List<HealthInfoDto> getRecommandArticles(User user) {
		// 开始代理
		List<HealthInfoDto> dtoes = new ArrayList<>();

		// 1、从缓存中获取
		// 2、缓存从数据苦里获取，并放入缓存
		// 3、数据库里没有，调用被代理的类，返回的结果放入数据库，并放入缓存，并返回
		
		// 暂时只用数据库保存，不加缓存保存，便于修改，如果以后加入了ehcache JMX管理后再改回来
		Date date = new Date();
//		String key = user.getId() + "_" + DateUtils.covertToYYYYMMDDStr(date) + "_" + "articles";
		HealthInfoDiscovery healthInfoDiscovery = this.healthInfoDiscoveryRepository.findByUserIdAndDiscoveryDate(
				user.getId(), DateUtils.covertToYYYYMMDD(date));
		if (healthInfoDiscovery == null) {
			dtoes.addAll(this.discoveryService.getRecommandArticles(user));
			if (dtoes.size() > 0) { // 有推荐才保存数据库
				healthInfoDiscovery = new HealthInfoDiscovery();
				healthInfoDiscovery.setDiscoveryDate(DateUtils.covertToYYYYMMDD(date));
				healthInfoDiscovery.setDiscoveryHealthInfoIds(toHealthInfoDtoIdStrs(dtoes));
				healthInfoDiscovery.setUser(user);
				this.healthInfoDiscoveryRepository.save(healthInfoDiscovery);
			}
			
		} else {
			dtoes.addAll(getHealthInfoDtoListFromStrids(healthInfoDiscovery.getDiscoveryHealthInfoIds()));
		}
		
		// 计算是否点击此用户是否点击过
		setDtoIsClicked(user, dtoes);
		
		return dtoes;
		
	}
	
	@Override
	public HealthInfoClickInfo clickHealthInfo(User user, HealthInfo healthInfo) {
		// 不用缓存，直接返回
		return this.discoveryService.clickHealthInfo(user, healthInfo);
	}
	
	@Override
	public HealthInfoDto detailHealthInfo(HealthInfo healthInfo) {
		// 不用缓存，直接返回
		return this.discoveryService.detailHealthInfo(healthInfo);
	}
	
	@Override
	public void addUserCategoryRelated(Long userId, Long categoryId) {
		// 删除缓存的推荐标签和推荐文章
		Date date = new Date();
		HealthCategoryDiscovery healthCategoryDiscovery = this.healthCategoryDiscoveryRepository.findByUserIdAndDiscoveryDate(
			userId, DateUtils.covertToYYYYMMDD(date));
		HealthInfoDiscovery healthInfoDiscovery = this.healthInfoDiscoveryRepository.findByUserIdAndDiscoveryDate(
			userId, DateUtils.covertToYYYYMMDD(date));
		if (healthCategoryDiscovery != null)
			this.healthCategoryDiscoveryRepository.delete(healthCategoryDiscovery);
		if (healthInfoDiscovery != null)
			this.healthInfoDiscoveryRepository.delete(healthInfoDiscovery);
		
		this.discoveryService.addUserCategoryRelated(userId, categoryId);
	}
	
	@Override
	public void deleteUserCategoryRelated(Long userId, Long categoryId) {
		// 删除缓存的推荐标签和推荐文章
		Date date = new Date();
		HealthCategoryDiscovery healthCategoryDiscovery = this.healthCategoryDiscoveryRepository.findByUserIdAndDiscoveryDate(
			userId, DateUtils.covertToYYYYMMDD(date));
		HealthInfoDiscovery healthInfoDiscovery = this.healthInfoDiscoveryRepository.findByUserIdAndDiscoveryDate(
			userId, DateUtils.covertToYYYYMMDD(date));
		if (healthCategoryDiscovery != null)
			this.healthCategoryDiscoveryRepository.delete(healthCategoryDiscovery);
		if (healthInfoDiscovery != null)
			this.healthInfoDiscoveryRepository.delete(healthInfoDiscovery);
		
		this.discoveryService.deleteUserCategoryRelated(userId, categoryId);
	}
}
