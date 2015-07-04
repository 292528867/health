package com.wonders.xlab.healthcloud.service.cache;

import com.wonders.xlab.healthcloud.repository.DrugDictionaryRepository;
import net.sf.ehcache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * Created by wukai on 15/7/3.
 */
public class ApplicationStartUp implements ApplicationListener<ApplicationContextEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        Cache luceneCache = (Cache) applicationContextEvent.getApplicationContext().getBean("luceneCache");
        DrugDictionaryRepository drugRepository = applicationContextEvent.getApplicationContext().getBean(DrugDictionaryRepository.class);
        LuceneCacheSvcImpl cacheSvc = new LuceneCacheSvcImpl();
        cacheSvc.setLuceneCache(luceneCache);
        cacheSvc.setDrugRepository(drugRepository);
        cacheSvc.initLuceneCache();
    }
}
