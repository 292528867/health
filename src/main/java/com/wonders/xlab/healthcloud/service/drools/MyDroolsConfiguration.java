package com.wonders.xlab.healthcloud.service.drools;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel.KieSessionType;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Drools配置。
 * @author xu
 *
 */
@Configuration
public class MyDroolsConfiguration {
	
	/**
	 * 创建kieServices。
	 * @return
	 */
	@Bean
	public KieServices myKieServices() {
		// Drools v6开始引入kie统一接口，定义方法和v5差的很远
		// 1、创建kieservices
		KieServices kieServices = KieServices.Factory.get();
		return kieServices;
	}
	
	/**
	 * 创建discovery规则KBase
	 * @return
	 */
	@Bean
	public KieBase discoveryKBase(KieServices myKieServices) {
		// Drools v6开始引入kie统一接口，定义方法和v5差的很远
		KieServices kieServices = myKieServices;
		
		// 2、创建KieModuleModel，一般情况下在类路径下创建kmodule.xml，这里变编程方式创建
		KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
		// 2.1、创建KieBaseModel，类似kmodule.xml中的kbase标签
		KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("discoveryKBase")
			.setDefault(true)
			.setEqualsBehavior(EqualityBehaviorOption.EQUALITY) 
			.setEventProcessingMode(EventProcessingOption.STREAM);
		// 2.2、创建与kbase关联的KieSessionModel，类似kmodule.xml中的kbase内的ksession标签
		kieBaseModel1.newKieSessionModel("discoveryKBaseSession") 
			.setDefault(true) 
			.setType(KieSessionType.STATEFUL)
			.setClockType(ClockTypeOption.get("realtime"));
		
		// 3、创建KieFileSystem，将模型xml，drl等写入
		KieFileSystem kfs = kieServices.newKieFileSystem();
		// 3.1、写入KieBaseModel（内部包含了KieSessionModel的内容了，注意之前的KieSessionModel的创建方式）
		kfs.writeKModuleXML(kieModuleModel.toXML());
		
		// 3.2，写入drl（写法众多，感觉有点乱）
		// 注意kfs写的时候如果指定path，强制为src/main/resources/加上文件名，还有就是文件名不要重复否则会覆盖的
		kfs.write("src/main/resources/DayPushHealthInfo.drl", kieServices.getResources().newInputStreamResource(
			this.getClass().getResourceAsStream("/com/wonders/xlab/healthcloud/service/drools/discovery/article/rule/DayPushHealthInfo.drl"), "UTF-8"));
		
		// 4、创建KieBuilder，使用KieFileSystem构建
		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
		Results results = kieBuilder.getResults();
		if (results.hasMessages(Message.Level.ERROR)) 
			throw new IllegalStateException("构建drools6错误：" + results.getMessages());
		
		// 5、获取KieContainer
		ReleaseId releaseId = kieServices.getRepository().getDefaultReleaseId(); // id用处很大，以后再议
		KieContainer kieContainer = kieServices.newKieContainer(releaseId);
		
		// 6、创建kbase
		KieBaseConfiguration kieBaseConf = kieServices.newKieBaseConfiguration();
		KieBase kieBase = kieContainer.newKieBase("discoveryKBase", kieBaseConf);
		
		return kieBase;
	}
}
