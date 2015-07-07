package com.wonders.xlab.healthcloud.service.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.wonders.xlab.healthcloud.service.cache.EhCacheConfiguration;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.DiscoveryArticleRuleService;

/**
 * 测试MyDroolsConfiguration。
 * @author xu
 *
 */
public class MyDroolsApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MyDroolsConfiguration.class, DiscoveryArticleRuleService.class);
		app.setWebEnvironment(false);
		ApplicationContext ctx = app.run(args);
		
		DiscoveryArticleRuleService discoveryArticleRuleService = ctx.getBean(DiscoveryArticleRuleService.class);
		discoveryArticleRuleService.testRule();
		
	}
}