package com.wonders.xlab.healthcloud.service.drools;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wonders.xlab.healthcloud.service.drools.fact.HealthInfoSample;
import com.wonders.xlab.healthcloud.service.drools.fact.UserQuerySample;

/**
 * discovery规则服务。
 * @author xu
 *
 */
@Service
public class DiscoveryRuleService {
	@Autowired
	@Qualifier("discoveryKBase")
	private KieBase kieBase;
	
	public void testRule() {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		
		// 1.1 设置gloable对象，在drl中通过别名使用
//		List<FeedDayDiagnoseOutput> diagnoseList = new ArrayList<FeedDayDiagnoseOutput>();
//		session.setGlobal("diagnoseList", diagnoseList);
		
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
