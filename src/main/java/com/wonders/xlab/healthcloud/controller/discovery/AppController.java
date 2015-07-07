package com.wonders.xlab.healthcloud.controller.discovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wonders.xlab.healthcloud.dto.discovery.HealthCategoryDto;
import com.wonders.xlab.healthcloud.dto.discovery.HealthInfoDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoClickInfoRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoRepository;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.DiscoveryArticleRuleService;
import com.wonders.xlab.healthcloud.service.drools.discovery.tag.DiscoveryTagRuleService;
import com.wonders.xlab.healthcloud.utils.DateUtils;

/**
 * discovery app前端控制器。
 * @author xu
 *
 */
@RestController
@RequestMapping(value = "discovery/app")
public class AppController {
	/** 日志记录器 */
	private static final Logger logger = LoggerFactory.getLogger("com.wonders.xlab.healthcloud.controller.discovery.AppController");
	
	@Autowired
	@Qualifier("discoveryArticleRuleService")
	private DiscoveryArticleRuleService discoveryArticleRuleService;
	@Autowired
	@Qualifier("discoveryTagRuleService")
	private DiscoveryTagRuleService discoveryTagRuleService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HealthCategoryRepository healthCategoryRepository;
	@Autowired
	private HealthInfoRepository healthInfoRepository;
	@Autowired
	private HealthInfoClickInfoRepository healthInfoClickInfoRepository;
	
	// 显示更多信息，分类标签信息返回
	@RequestMapping(value = "recommand/tags/{userId}")
	public ControllerResult<?> getTagPushInfos(@PathVariable Long userId) {
		// 查询所有category
		List<HealthCategory> allCategories = this.healthCategoryRepository.findAll();
		// 查询用户
		User user = userRepository.queryUserHealthInfo(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在");
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
		List<HealthCategoryDto> healthCategoryDtoes = new ArrayList<>();
		for (HealthCategory hc : healthCategories_order) 
			healthCategoryDtoes.add(new HealthCategoryDto().toNewHealthCategoryDto(hc));
		return new ControllerResult<List<HealthCategoryDto>>().setRet_code(0).setRet_values(healthCategoryDtoes).setMessage("成功");
	}
	
	// 根据选择的标签，再次变化推荐的标签
	@RequestMapping(value = "recommand/tags/{userId}/{categoryId}")
	public ControllerResult<?> getTagPushInfos(@PathVariable Long userId, @PathVariable Long categoryId) {
		// 查询所有category
		List<HealthCategory> allCategories = this.healthCategoryRepository.findAll();
		// 查询用户
		User user = userRepository.queryUserHealthInfo(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在");
		// 获取单个分类
		HealthCategory healthCategory = this.healthCategoryRepository.findOne(categoryId);
		if (healthCategory == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("指定分类竟然不存在").setMessage("指定分类竟然不存在");
		Set<HealthCategory> userCategories = new HashSet<>();
		userCategories.add(healthCategory);
		
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
		List<HealthCategoryDto> healthCategoryDtoes = new ArrayList<>();
		for (HealthCategory hc : healthCategories_order) 
			healthCategoryDtoes.add(new HealthCategoryDto().toNewHealthCategoryDto(hc));
		return new ControllerResult<List<HealthCategoryDto>>().setRet_code(0).setRet_values(healthCategoryDtoes).setMessage("成功");
	}
	
	// 显示每个分类标签的健康info信息
	@RequestMapping(value = "recommand/tag/articles/{categoryId}/{userId}")
	public ControllerResult<?> getTagInfos(@PathVariable Long categoryId, @PathVariable Long userId) {
		// TODO：暂时返回分类标签的所有文章，之后使用规则引擎解决
		List<HealthInfo> healthInfos = this.healthInfoRepository.findByHealthCategoryId(categoryId);
		
		List<HealthInfoDto> healthInfoDtoes = new ArrayList<HealthInfoDto>();
		for (HealthInfo h : healthInfos) {
			HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(h); 
			// TODO：获取点击数需要规则
			Long count = this.healthInfoClickInfoRepository.healthInfoTotalClickCount(h.getId());
			dto.setClickCount(count == null ? 0 : count);
			healthInfoDtoes.add(dto);
		}
		
		return new ControllerResult<List<HealthInfoDto>>().setRet_code(0).setRet_values(healthInfoDtoes).setMessage("成功");
	}
	
	
	// 首页显示推荐健康info信息，TODO：逻辑还要修正，每天用户都一样
	@RequestMapping(value = "recommand/articles/{userId}")
	public ControllerResult<?> getHealthPushInfos(@PathVariable Long userId) {
		// 级连查出用户所有健康信息
		User user = userRepository.queryUserHealthInfo(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在！");
		Long[] ids = discoveryArticleRuleService.pushArticles(user);
		
		List<HealthInfo> healthInfos = this.healthInfoRepository.findAll(Arrays.asList(ids));
		List<HealthInfoDto> healthInfoDtoes = new ArrayList<HealthInfoDto>();
		for (HealthInfo h : healthInfos) {
			HealthInfoDto dto = new HealthInfoDto().toNewHealthInfoDto(h); 
			// TODO：获取点击数需要规则
			Long count = this.healthInfoClickInfoRepository.healthInfoTotalClickCount(h.getId());
			dto.setClickCount(count == null ? 0 : count);
			healthInfoDtoes.add(dto);
		}
			
		return new ControllerResult<List<HealthInfoDto>>().setRet_code(0).setRet_values(healthInfoDtoes).setMessage("成功");
	}
	
	// 点击某个标签，记录点击
	@RequestMapping(value = "clickInfo/{userId}/{infoId}")
	public ControllerResult<?> clickHealthInfo(@PathVariable Long userId, @PathVariable Long infoId) {
		User user = this.userRepository.findOne(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在！");
		HealthInfo healthInfo = this.healthInfoRepository.findOne(infoId);
		if (healthInfo == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("文章信息不存在").setMessage("文章信息不村子！");
		
		HealthInfoClickInfo clickInfo = this.healthInfoClickInfoRepository.findByUserIdAndClickDateAndHealthInfoId(
				userId, DateUtils.covertToYYYYMMDD(new Date()), healthInfo.getId());
		if (clickInfo == null) {
			clickInfo = new HealthInfoClickInfo();
			clickInfo.setClickCount(0);
			clickInfo.setClickDate(DateUtils.covertToYYYYMMDD(new Date()));
			clickInfo.setHealthInfo(healthInfo);
			clickInfo.setUser(user);
		}
		clickInfo.setClickCount(clickInfo.getClickCount() + 1);
		this.healthInfoClickInfoRepository.save(clickInfo);
		
		Long count = this.healthInfoClickInfoRepository.healthInfoTotalClickCount(infoId);
		return new ControllerResult<Long>().setRet_code(0).setRet_values(count).setMessage("成功！");
	}
	
	// 显示具体的健康info信息
	
}
