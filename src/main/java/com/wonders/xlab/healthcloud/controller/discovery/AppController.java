package com.wonders.xlab.healthcloud.controller.discovery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoRepository;
import com.wonders.xlab.healthcloud.service.discovery.DiscoveryService;

/**
 * discovery app前端控制器。
 * @author xu
 *
 */
@RestController
@RequestMapping(value = "discovery/app")
public class AppController {
	@Autowired
	@Qualifier("discoveryServiceProxy")
	private DiscoveryService discoveryService;
	@Autowired
	private HealthCategoryRepository healthCategoryRepository;
	@Autowired
	private HealthInfoRepository healthInfoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// 显示更多信息，分类标签信息返回
	@RequestMapping(value = "recommand/tags/{userId}")
	public ControllerResult<?> getTagPushInfos(@PathVariable Long userId) {
		// 查询用户
		User user = userRepository.queryUserHealthInfo(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在");
		// 服务执行
		List<HealthCategory> healthCategories = this.discoveryService.getRecommandTag(user);
		// 输出dto
		List<HealthCategoryDto> healthCategoryDtoes = new ArrayList<>();
		for (HealthCategory hc : healthCategories) 
			healthCategoryDtoes.add(new HealthCategoryDto().toNewHealthCategoryDto(hc));
		return new ControllerResult<List<HealthCategoryDto>>().setRet_code(0).setRet_values(healthCategoryDtoes).setMessage("成功");
	}
	
	// 根据选择的标签，再次变化推荐的标签
	@RequestMapping(value = "recommand/tags/{userId}/{categoryId}")
	public ControllerResult<?> getTagPushInfos(@PathVariable Long userId, @PathVariable Long categoryId) {
		// 查询用户
		User user = userRepository.queryUserHealthInfo(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在");
		// 获取单个分类
		HealthCategory healthCategory = this.healthCategoryRepository.findOne(categoryId);
		if (healthCategory == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("指定分类竟然不存在").setMessage("指定分类竟然不存在");
		// 服务执行
		List<HealthCategory> healthCategories = this.discoveryService.getRecommandTag(user, healthCategory);
		// 输出dto
		List<HealthCategoryDto> healthCategoryDtoes = new ArrayList<>();
		for (HealthCategory hc : healthCategories) 
			healthCategoryDtoes.add(new HealthCategoryDto().toNewHealthCategoryDto(hc));
		return new ControllerResult<List<HealthCategoryDto>>().setRet_code(0).setRet_values(healthCategoryDtoes).setMessage("成功");
	}
	
	// 显示每个分类标签的健康info信息
	@RequestMapping(value = "recommand/tag/articles/{categoryId}/{userId}", method = RequestMethod.GET)
	public ControllerResult<?> getTagInfos(@PathVariable Long categoryId, @PathVariable Long userId, Pageable pageable) {
		// 查询用户
		User user = userRepository.findOne(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在");
		// 获取单个分类
		HealthCategory healthCategory = this.healthCategoryRepository.findOne(categoryId);
		if (healthCategory == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("指定分类竟然不存在").setMessage("指定分类竟然不存在");
		// 服务执行
		Page<HealthInfoDto> healthInfoDtoes = this.discoveryService.getTagInfos(healthCategory, user, pageable);
		// 输出dto
		return new ControllerResult<Page<HealthInfoDto>>().setRet_code(0).setRet_values(healthInfoDtoes).setMessage("成功");
	}
	
	
	// 首页显示推荐健康info信息
	@RequestMapping(value = "recommand/articles/{userId}")
	public ControllerResult<?> getHealthPushInfos(@PathVariable Long userId) {
		// 级连查出用户所有健康信息
		User user = userRepository.queryUserHealthInfo(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在！");
		// 服务执行
		List<HealthInfoDto> healthInfoDtoes = this.discoveryService.getRecommandArticles(user);
		// 输出dto
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
		// 服务执行
		HealthInfoClickInfo clickInfo = this.discoveryService.clickHealthInfo(user, healthInfo);
		// 输出dto
		return new ControllerResult<Long>().setRet_code(0).setRet_values(clickInfo.getClickCount()).setMessage("成功！");
	}
	
	// 显示具体的健康info信息
	@RequestMapping(value = "listInfo/{healthInfoId}", method = RequestMethod.GET)
	public ControllerResult<?> listHealthInfo(@PathVariable Long healthInfoId) {
		HealthInfo hi = this.healthInfoRepository.findOne(healthInfoId);
		if (hi == null)
			return new ControllerResult<String>().setRet_code(0).setRet_values("文章不存在").setMessage("文章不存在");
		// 服务执行
		HealthInfoDto dto = this.discoveryService.detailHealthInfo(hi);
		return new ControllerResult<HealthInfoDto>().setRet_code(0).setRet_values(dto).setMessage("成功");
	}
	
}
