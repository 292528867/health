package com.wonders.xlab.healthcloud.controller.discovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wonders.xlab.healthcloud.dto.discovery.HealthInfoDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoRepository;
import com.wonders.xlab.healthcloud.service.drools.DiscoveryRuleService;

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
	private DiscoveryRuleService discoveryRuleService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HealthInfoRepository healthInfoRepository;
	
	// 首页显示推荐健康info信息
	@RequestMapping(value = "recommand/articles/{userId}")
	public ControllerResult<?> getHealthPUshInfos(@PathVariable Long userId) {
		// 级连查出用户所有健康信息
		User user = userRepository.queryUserHealthInfo(userId);
		if (user == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在").setMessage("用户不存在！");
		Long[] ids = discoveryRuleService.pushArticles(user);
		
		List<HealthInfo> healthInfos = this.healthInfoRepository.findAll(Arrays.asList(ids));
		List<HealthInfoDto> healthInfoDtoes = new ArrayList();
		for (HealthInfo h : healthInfos)
			healthInfoDtoes.add(new HealthInfoDto().toNewHealthInfoDto(h));
		return new ControllerResult<List<HealthInfoDto>>().setRet_code(0).setRet_values(healthInfoDtoes).setMessage("成功");
	}
	
	// 显示更多信息，分类标签信息返回
	
	// 显示每个分类标签的健康info信息
	
	// 显示具体的健康info信息
	
}
