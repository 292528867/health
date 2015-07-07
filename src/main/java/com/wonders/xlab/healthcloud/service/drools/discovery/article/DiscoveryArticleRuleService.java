package com.wonders.xlab.healthcloud.service.drools.discovery.article;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;
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
	 * 根据规则计算指定用户可推送健康信息文章id。
	 * @param user user里的对象必须级连查出了
	 */
	public Long[] pushArticles(User user) {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		// 2、构造global对象，分析后返回
		OutputDaytHealthInfo output = new OutputDaytHealthInfo();
		session.setGlobal("articleoutput", output);
		
		// 3、构建fact放入规则中
		UserQuerySample userQuerySample = new UserQuerySample(user.getId());
		session.insert(userQuerySample);
		
		for (HealthCategory category : user.getHcs()) {
			for (HealthInfo healthInfo : category.getHins()) {
				int clickCount = 0;
				for (HealthInfoClickInfo healthInfoClickInfo : healthInfo.getHicis()) 
					clickCount += healthInfoClickInfo.getClickCount();
				// 创建sample fact
				HealthInfoSample healthInfoSample = new HealthInfoSample(
						user.getId(), 
						healthInfo.getId(), 
						healthInfo.getTitle(), 
						clickCount);
				session.insert(healthInfoSample);
			}
		}
		
		// 4、执行rule
		session.fireAllRules();
		// 5、销毁session
		session.dispose();
		
		return output.getHealthInfoIds();
	}
	
	public void testRule() {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		
		// 1.1 设置gloable对象，在drl中通过别名使用
		OutputDaytHealthInfo output = new OutputDaytHealthInfo();
		session.setGlobal("output", output);
		
		// 1.2 可以设置一些监听器，再议
		
		// 2、创建fact对象
		UserQuerySample querySample = new UserQuerySample(1L);
		for (long i = 28; i >= 0; i--) 
			session.insert(new HealthInfoSample(1L, i, "title" + i, new Long(i).intValue()));
		
		session.insert(querySample);
		
		// 3、执行rule
		session.fireAllRules();
		
		
		// 4、执行完毕销毁，有日志的也要关闭
		session.dispose();
	}
}
