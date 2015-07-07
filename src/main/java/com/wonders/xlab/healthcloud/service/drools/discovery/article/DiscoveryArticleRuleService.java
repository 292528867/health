package com.wonders.xlab.healthcloud.service.drools.discovery.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.HealthInfoClickSample;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.HealthInfoSample;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.input.UserQuerySample;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.output.OutputDaytHealthInfo;
import com.wonders.xlab.healthcloud.service.drools.discovery.article.output.OutputHealthInfoClickCount;


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
	 * @param A A值
	 * @param X X值
	 * @param sample 样本
	 * @return
	 */
	public Map<Long, Long> calcuClickCount(int A, double X, List<HealthInfoSample> sampleList) {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		// 2、构造global对象，分析后返回
		session.setGlobal("clickCount_A", A);
		session.setGlobal("clickCount_X", X);
		HashMap<Long, Long> output = new HashMap<>();
		session.setGlobal("clickCountOutputMap", output);
		
		// 3、构建fact放入规则中
		for (HealthInfoSample sample : sampleList) {
			HealthInfoClickSample sample_2 = new HealthInfoClickSample(
				sample.getHealthInfoId(), 
				sample.getCreateTime(), 
				new Long(sample.getClickCount()));
			session.insert(sample_2);
		}

		// 4、执行rule
		session.fireAllRules();
		// 5、销毁session
		session.dispose();
		
		return output;
	}
	
	/**
	 * 根据规则计算指定用户可推送健康信息文章id。
	 * @param user user里的对象必须级连查出了
	 */
	public Map<Long, Long> pushArticles(User user, int articleCount) {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		// 2、构造global对象，分析后返回
		OutputDaytHealthInfo output = new OutputDaytHealthInfo();
		session.setGlobal("articleoutput", output);
		session.setGlobal("articleCount", articleCount);
		
		// 3、构建fact放入规则中
		UserQuerySample userQuerySample = new UserQuerySample(user.getId());
		session.insert(userQuerySample);
		
		List<HealthInfoSample> healthInfoSampleList = new ArrayList<>();
		
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
				healthInfoSample.setCreateTime(healthInfo.getCreatedDate());
				healthInfoSampleList.add(healthInfoSample);
			}
		}
		
		// 重新计算clickCount
		Map<Long, Long> clickCounts = this.calcuClickCount(20, 0.1, healthInfoSampleList);
		for (HealthInfoSample sample : healthInfoSampleList) 
			sample.setClickCount(clickCounts.get(sample.getHealthInfoId()).intValue());
		for (HealthInfoSample sample : healthInfoSampleList) 
			session.insert(sample);
		
		
		// 4、执行rule
		session.fireAllRules();
		// 5、销毁session
		session.dispose();
		
		Long[] ids = output.getHealthInfoIds();
		Map<Long, Long> returnMapIds = new HashMap<>();
		for (Long id : ids) 
			returnMapIds.put(id, clickCounts.get(id));
		
		return returnMapIds;
	}
	
	public void testRule() {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		
		// 1.1 设置gloable对象，在drl中通过别名使用
		OutputDaytHealthInfo output = new OutputDaytHealthInfo();
		session.setGlobal("articleoutput", output);
		
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
