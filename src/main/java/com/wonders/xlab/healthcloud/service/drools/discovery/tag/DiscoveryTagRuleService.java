package com.wonders.xlab.healthcloud.service.drools.discovery.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.service.drools.discovery.tag.input.TagInfoSample;
import com.wonders.xlab.healthcloud.service.drools.discovery.tag.input.UserTagQuerySample;
import com.wonders.xlab.healthcloud.service.drools.discovery.tag.output.OutputTagInfo;

@Service
public class DiscoveryTagRuleService {
	@Autowired
	@Qualifier("discoveryKBase")
	private KieBase kieBase;
	
	/**
	 * 根据规则计算推荐分类标签。
	 * @param user 用户
	 * @param allHealthCategories 所有标签分类信息
	 * @param userHealthCategories 用户选择的分类信息
	 * @return
	 */
	public Long[] pushTags(User user, List<HealthCategory> allHealthCategories, Set<HealthCategory> userHealthCategories) {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		
		// 1.1 设置gloable对象，在drl中通过别名使用
		List<OutputTagInfo> output = new ArrayList<>();
		session.setGlobal("tagoutput", output);
		
		// 1.2 可以设置一些监听器，再议
		
		// 2、创建fact对象
		for (HealthCategory hc : userHealthCategories) {
			UserTagQuerySample us = new UserTagQuerySample();
			us.setUserId(user.getId());
			us.setTagId(hc.getId());
			us.setTagName(hc.getTag());
			session.insert(us);
		}
		for (HealthCategory hc : allHealthCategories) {
			String[] r1_ids = StringUtils.split(hc.getFirstRelatedIds() == null ? "" : hc.getFirstRelatedIds(), ",");
			String[] rn_ids = StringUtils.split(hc.getOtherRelatedIds() == null ? "" : hc.getOtherRelatedIds(), ",");
			
			Long[] r1_ids_long = new Long[r1_ids.length];
			for (int i = 0; i < r1_ids.length; i++) 
				r1_ids_long[i] = Long.parseLong(r1_ids[i]);
			Long[] rn_ids_long = new Long[rn_ids.length];
			for (int i = 0; i < rn_ids.length; i++)
				rn_ids_long[i] = Long.parseLong(rn_ids[i]);
			
			TagInfoSample sample = new TagInfoSample(
				hc.getId(),
				hc.getTag(),
				Arrays.asList(r1_ids_long),
				Arrays.asList(rn_ids_long)
			);
			session.insert(sample);
		}
		
		// 3、执行rule
		session.fireAllRules();
		
		// 4、执行完毕销毁，有日志的也要关闭
		session.dispose();
		
		if (output.size() == 0) 
			return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		else {
			List<Long> ids = output.get(0).getPushTagIds();
			Long[] rids = new Long[ids.size()];
			for (int i = 0; i < ids.size(); i++) 
				rids[i] = ids.get(i);
			
			System.out.println(Arrays.asList(rids));
			
			return rids;
		}
			
	}
	
	public void testRule() {
		// 1、创建session，内部配置的是stateful
		KieSession session = kieBase.newKieSession();
		
		// 1.1 设置gloable对象，在drl中通过别名使用
		List<OutputTagInfo> output = new ArrayList<>();
		session.setGlobal("tagoutput", output);
		
		// 1.2 可以设置一些监听器，再议
		
		// 2、创建fact对象
		UserTagQuerySample us = new UserTagQuerySample();
		us.setUserId(1L);
		us.setTagId(1L);
		us.setTagName("tag1");
		UserTagQuerySample us2 = new UserTagQuerySample();
		us2.setUserId(1L);
		us2.setTagId(2L);
		us2.setTagName("tag2");
		
		
		TagInfoSample sample1 = new TagInfoSample(
			1L, 
			"tag1",
			Arrays.asList(new Long[] {2L, 3L, 4L}), 
			Arrays.asList(new Long[] {4L, 5L}));
		TagInfoSample sample2 = new TagInfoSample(
			2L, 
			"tag1",
			Arrays.asList(new Long[] {6L}), 
			Arrays.asList(new Long[] {1L, 4L}));
		TagInfoSample sample3 = new TagInfoSample(
			3L, 
			"tag1",
			Arrays.asList(new Long[] {2L, 3L}), 
			Arrays.asList(new Long[] {4L, 5L}));
		TagInfoSample sample4 = new TagInfoSample(
			4L, 
			"tag1",
			Arrays.asList(new Long[] {2L, 3L}), 
			Arrays.asList(new Long[] {4L, 5L}));
		TagInfoSample sample5 = new TagInfoSample(
			5L, 
			"tag1",
			Arrays.asList(new Long[] {2L, 3L}), 
			Arrays.asList(new Long[] {4L, 5L}));
		TagInfoSample sample6 = new TagInfoSample(
			6L, 
			"tag1",
			Arrays.asList(new Long[] {2L, 3L}), 
			Arrays.asList(new Long[] {4L, 5L}));
		TagInfoSample sample7 = new TagInfoSample(
			7L, 
			"tag1",
			Arrays.asList(new Long[] {2L, 3L}), 
			Arrays.asList(new Long[] {4L, 5L}));
		
		session.insert(us);
		session.insert(us2);
		session.insert(sample1);
		session.insert(sample2);
		session.insert(sample3);
		session.insert(sample4);
		session.insert(sample5);
		session.insert(sample6);
		session.insert(sample7);
		
		// 3、执行rule
		session.fireAllRules();
		
		// 4、执行完毕销毁，有日志的也要关闭
		session.dispose();
	}
}
