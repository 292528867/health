package com.wonders.xlab.healthcloud.service.drools;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * 测试MyDroolsConfiguration。
 * @author xu
 *
 */
public class MyDroolsApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MyDroolsConfiguration.class, DiscoveryRuleService.class);
		app.setWebEnvironment(false);
		ApplicationContext ctx = app.run(args);
		
		DiscoveryRuleService discoveryRuleService = ctx.getBean(DiscoveryRuleService.class);
		discoveryRuleService.testRule();
		
//		// 获取feed规则服务执行
//		FeedRuleService feedRuleService = ctx.getBean(FeedRuleService.class);
//		feedRuleService.feedRule();
////		feedRuleService.feedInfoUpdate();
//		
//		// 获取poo规则服务执行
//		PooRuleService pooRuleService = ctx.getBean(PooRuleService.class);
////		pooRuleService.pooRule();
	}
}