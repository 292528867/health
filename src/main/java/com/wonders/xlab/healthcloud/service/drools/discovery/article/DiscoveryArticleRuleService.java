package com.wonders.xlab.healthcloud.service.drools.discovery.article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.HealthInfoClickSample;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.HealthInfoSample;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.UserQuerySample;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.output.OutputDaytHealthInfo;


/**
 * 发现文章规则服务，
 * 1、找出用户关心分类的所有文章
 * 2、找出所有文章中所有点击数为0的文章
 * 3、如果点击数为0的文章数大于等于需要的文章数，随机选取需要的文章数
 * 4、如果点击数为0的文章数不够，多余到点击数大于0的文章
 * 5、点击数超过0的文章点击数从小到大排序，然后取剩余未选择的文章数
 * @author xu
 */
@Service
public class DiscoveryArticleRuleService {
	@Autowired
	@Qualifier("discoveryKBase")
	private KieBase kieBase;
	
	/**
	 * 重新计算点击数。
	 * @param X X值
	 * @param sample 样本
	 * @return Map(key=文章id，value=文章点击数)
	 */
	public Map<Long, Long> calcuClickCount(double X, HealthInfoClickSample... sampleList) {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		// 2、构造global对象，分析后返回
		session.setGlobal("clickCount_X", X);
		HashMap<Long, Long> output = new HashMap<>();
		session.setGlobal("clickCountOutputMap", output);
		
		// 3、构建fact放入规则中
		for (HealthInfoClickSample sample : sampleList)
			session.insert(sample);

		// 4、执行rule
		session.fireAllRules();
		// 5、销毁session
		session.dispose();
		
		return output;
	}
	
	/**
	 * 根据规则计算指定用户可推送健康信息文章id。
	 * @param userId 用户id
	 * @param healthInfos 健康文章info
	 * @param articleCount 欲选择的文章数
	 * @return List<Long> 推荐的文章id列表
	 */
	public List<HealthInfo> pushArticles(Long userId, List<HealthInfoSample> healthInfoSamples, int articleCount) {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		// 2、构造global对象，分析后返回
		OutputDaytHealthInfo output = new OutputDaytHealthInfo();
		session.setGlobal("articleoutput", output);
		session.setGlobal("articleCount", articleCount);
		
		// 3、构建fact放入规则中
		UserQuerySample userQuerySample = new UserQuerySample(userId);
		session.insert(userQuerySample);
		
		for (HealthInfoSample healthInfoSample : healthInfoSamples) 
			session.insert(healthInfoSample);
		
		// 4、执行rule
		session.fireAllRules();
		// 5、销毁session
		session.dispose();
		
		return output.getBoes();
	}
}
