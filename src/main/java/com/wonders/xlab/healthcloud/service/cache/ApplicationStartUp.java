package com.wonders.xlab.healthcloud.service.cache;

import com.wonders.xlab.healthcloud.repository.DrugDictionaryRepository;
import com.wonders.xlab.healthcloud.utils.ThirdAppConnUtils;
import net.sf.ehcache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * Created by wukai on 15/7/3.
 */
public class ApplicationStartUp implements ApplicationListener<ApplicationContextEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        Cache luceneCache = (Cache) event.getApplicationContext().getBean("luceneCache");
        DrugDictionaryRepository drugRepository = event.getApplicationContext().getBean(DrugDictionaryRepository.class);
        LuceneCacheSvcImpl cacheSvc = new LuceneCacheSvcImpl();
        cacheSvc.setLuceneCache(luceneCache);
        cacheSvc.setDrugRepository(drugRepository);
        cacheSvc.initLuceneCache();

        ThirdAppConnUtils.initAppDatas();

        /*// 初始内存问卷
        Cache questionnaireCache = (Cache) applicationContextEvent.getApplicationContext().getBean("questionnaireCache");
        DrugDictionaryRepository questionRepository = applicationContextEvent.getApplicationContext().getBean(DrugDictionaryRepository.class);
        LuceneCacheSvcImpl questionCacheSvc = new LuceneCacheSvcImpl();
        questionCacheSvc.setLuceneCache(questionnaireCache);
        questionCacheSvc.setDrugRepository(questionRepository);
        questionCacheSvc.initLuceneCache();*/

    }
}
