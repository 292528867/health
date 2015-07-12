package com.wonders.xlab.healthcloud.service.discovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.ehcache.Cache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.DiscoveryArticleRuleService;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.HealthInfoSample;
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
	@Qualifier("discoveryCache")
	private Cache discoveryCache;
	/** 缓存代理（key="{用户id}_{yyyy-MM-dd}"，value={HealthInfoDayDiscovery对象}） */
	private HCCache<String, Object> proxyCache;
	
	@PostConstruct
	private void initBean() {
		proxyCache = new HCCacheProxy<String, Object>(discoveryCache);
	}
	
	private void setDtoIsClicked(User user, List<HealthInfoDto> dtoes) {
		Map<Long, Long> userClicked = new HashMap<>();
		List<Object> allClickCountList = this.healthInfoClickInfoRepository.healthInfoTotalClickCountWithUserId(user.getId());
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
	
	private void setDtoClickCount(List<HealthInfoDto> dtoes) {
		List<HealthInfoSample> healthInfoSampleList = new ArrayList<>();
		Map<Long, Long> allClickCount = new HashMap<>();
		List<Object> allClickCountList = this.healthInfoClickInfoRepository.healthInfoTotalClickCount();
		for (Object record : allClickCountList) {
			Object[] record_values = (Object[]) record;
			allClickCount.put((Long) record_values[0], (Long) record_values[1]);
		}
		for (HealthInfoDto healthInfoDto : dtoes) {
			// 创建sample fact
			HealthInfoSample healthInfoSample = new HealthInfoSample(
					0L, 
					healthInfoDto.getCreateTime(),
					healthInfoDto.getId(), 
					healthInfoDto.getTitle(), 
					allClickCount.get(healthInfoDto.getId()) == null ? 0 : allClickCount.get(healthInfoDto.getId()), 
					healthInfoDto.getClickCount_A()
				);
			healthInfoSampleList.add(healthInfoSample);
		}
		
		Map<Long, Long> clickCounts = this.discoveryArticleRuleService.calcuClickCount(0.1, healthInfoSampleList);
		for (HealthInfoDto dto : dtoes) 
			dto.setClickCount(clickCounts.get(dto.getId()) == null ? 0 : clickCounts.get(dto.getId()));
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
		
		List<HealthInfo> healthInfos = this.healthInfoRepository.findAll(ids_long_list);
		List<HealthInfoDto> dtoes = new ArrayList<>();
		for (HealthInfo healthInfo : healthInfos) 
			dtoes.add(new HealthInfoDto().toNewHealthInfoDto(healthInfo));
		return dtoes;
	}
	
	@Override
	public List<HealthCategory> getRecommandTag(User user) {
		// 开始代理
		List<HealthCategory> healthCategoryList = new ArrayList<>();

		// 1、从缓存中获取
		// 2、缓存从数据苦里获取，并放入缓存
		// 3、数据库里没有，调用被代理的类，返回的结果放入数据库，并放入缓存，并返回
		Date date = new Date();
		String key = user.getId() + "_" + DateUtils.covertToYYYYMMDDStr(date) + "_" + "tags";
		HealthCategoryDiscovery healthCategoryDiscovery = (HealthCategoryDiscovery) proxyCache.getFromCache(key);
		if (healthCategoryDiscovery != null) {
			healthCategoryList.addAll(getHealthCategoryListFromStrids(healthCategoryDiscovery.getDiscoveryHealthCategoryIds()));
			return healthCategoryList;
		} else {
			healthCategoryDiscovery = this.healthCategoryDiscoveryRepository.findByUserIdAndDiscoveryDate(
				user.getId(), DateUtils.covertToYYYYMMDD(date));
			if (healthCategoryDiscovery != null) {
				this.proxyCache.addToCache(key, healthCategoryDiscovery);
				healthCategoryList.addAll(getHealthCategoryListFromStrids(healthCategoryDiscovery.getDiscoveryHealthCategoryIds()));
				return healthCategoryList;
			} else { 
				healthCategoryList.addAll(this.discoveryService.getRecommandTag(user));
				healthCategoryDiscovery = new HealthCategoryDiscovery();
				healthCategoryDiscovery.setDiscoveryDate(DateUtils.covertToYYYYMMDD(date));
				healthCategoryDiscovery.setDiscoveryHealthCategoryIds(toHealthCategoryIdStrs(healthCategoryList));
				healthCategoryDiscovery.setUser(user);
				this.healthCategoryDiscoveryRepository.save(healthCategoryDiscovery);
				this.proxyCache.addToCache(key, healthCategoryDiscovery);
				return healthCategoryList;
			}
		}
	}
	
	@Override
	public List<HealthCategory> getRecommandTag(User user,
			HealthCategory... categories) {
		// 不用缓存，直接返回
		return this.discoveryService.getRecommandTag(user, categories);
	}
	
	@Override
	public List<HealthInfoDto> getTagInfos(HealthCategory healthCategory,
			User user) {
		List<HealthInfoDto> dtoes = this.discoveryService.getTagInfos(healthCategory, user);
		if (dtoes.size() == 0) 
			return dtoes;
		
		// 将今日推荐的文章去除
		Date date = new Date();
		String key = user.getId() + "_" + DateUtils.covertToYYYYMMDDStr(date) + "_" + "articles";
		HealthInfoDiscovery healthInfoDiscovery = (HealthInfoDiscovery) proxyCache.getFromCache(key);
		if (healthInfoDiscovery != null) {
			String ids_str = healthInfoDiscovery.getDiscoveryHealthInfoIds();
			List<String> ids_str_array = Arrays.asList(StringUtils.split(ids_str, ","));
			Iterator<HealthInfoDto> iter = dtoes.iterator();
			if (CollectionUtils.isNotEmpty(ids_str_array)) {
				while (iter.hasNext()) {
					HealthInfoDto dto = iter.next();
					if (ids_str_array.contains(dto.getId().toString())) 
						iter.remove();
				}
			}
		}
		
		// 将dto排序
		Collections.sort(dtoes, new Comparator<HealthInfoDto>() {
			@Override
			public int compare(HealthInfoDto o1, HealthInfoDto o2) {
				return new CompareToBuilder()
					.append(o1.getClickCount(), o2.getClickCount())
					.append(o1.getCreateTime(), o2.getCreateTime())
					.toComparison();
			}
		});
		
		// 计算是否点击此用户是否点击过
		setDtoIsClicked(user, dtoes);
		return dtoes;
	}
	
	@Override
	public List<HealthInfoDto> getRecommandArticles(User user) {
		// 开始代理
		List<HealthInfoDto> dtoes = new ArrayList<>();

		// 1、从缓存中获取
		// 2、缓存从数据苦里获取，并放入缓存
		// 3、数据库里没有，调用被代理的类，返回的结果放入数据库，并放入缓存，并返回
		Date date = new Date();
		String key = user.getId() + "_" + DateUtils.covertToYYYYMMDDStr(date) + "_" + "articles";
		HealthInfoDiscovery healthInfoDiscovery = (HealthInfoDiscovery) proxyCache.getFromCache(key);
		if (healthInfoDiscovery != null) {
			dtoes.addAll(getHealthInfoDtoListFromStrids(healthInfoDiscovery.getDiscoveryHealthInfoIds()));
			// 重新计算点击量
			setDtoClickCount(dtoes);
			// 计算是否点击此用户是否点击过
			setDtoIsClicked(user, dtoes);
			return dtoes;
		} else {
			healthInfoDiscovery = this.healthInfoDiscoveryRepository.findByUserIdAndDiscoveryDate(
				user.getId(), DateUtils.covertToYYYYMMDD(date));
			if (healthInfoDiscovery != null) {
				this.proxyCache.addToCache(key, healthInfoDiscovery);
				dtoes.addAll(getHealthInfoDtoListFromStrids(healthInfoDiscovery.getDiscoveryHealthInfoIds()));			
				// 重新计算点击量
				setDtoClickCount(dtoes);
				// 计算是否点击此用户是否点击过
				setDtoIsClicked(user, dtoes);
				
				return dtoes;
			} else { 
				dtoes.addAll(this.discoveryService.getRecommandArticles(user));
				healthInfoDiscovery = new HealthInfoDiscovery();
				healthInfoDiscovery.setDiscoveryDate(DateUtils.covertToYYYYMMDD(date));
				healthInfoDiscovery.setDiscoveryHealthInfoIds(toHealthInfoDtoIdStrs(dtoes));
				healthInfoDiscovery.setUser(user);
				this.healthInfoDiscoveryRepository.save(healthInfoDiscovery);
				this.proxyCache.addToCache(key, healthInfoDiscovery);
				
				// 计算是否点击此用户是否点击过
				setDtoIsClicked(user, dtoes);
				
				return dtoes;
			}
		}
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
}
