package com.wonders.xlab.healthcloud.service.cache;

import com.wonders.xlab.healthcloud.entity.DrugDictionary;
import com.wonders.xlab.healthcloud.repository.DrugDictionaryRepository;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wukai on 15/7/3.
 */
@Service
public class LuceneCacheSvcImpl implements LuceneCacheService {
    @Autowired
    private DrugDictionaryRepository drugRepository;

    @Autowired
    @Qualifier("luceneCache")
    private Cache luceneCache;

    /**
     * 初始化药品名缓存
     */
    @Override
    public void initLuceneCache() {
        HCCacheProxy<String, String> proxy = new HCCacheProxy(luceneCache);

        List<DrugDictionary> drugList = drugRepository.findAll();
        for(DrugDictionary drug : drugList){
            proxy.addToCache(drug.getName(), "1");
            proxy.addToCache(drug.getGname(), "1");
            if(StringUtils.isNotBlank(drug.getName())){
                if(drug.getName().indexOf("(") > -1){
                    proxy.addToCache(drug.getName().substring(0, drug.getName().indexOf("(")), "1");
                }
                if(drug.getName().indexOf("（") > -1){
                    proxy.addToCache(drug.getName().substring(0, drug.getName().indexOf("（")), "1");
                }
            }
            if(StringUtils.isNotBlank(drug.getGname())){
                if(drug.getGname().indexOf("（") > -1){
                    proxy.addToCache(drug.getGname().substring(0, drug.getGname().indexOf("（")), "1");
                }
                if(drug.getGname().indexOf("(") > -1){
                    proxy.addToCache(drug.getGname().substring(0, drug.getGname().indexOf("(")), "1");
                }
            }
        }
//        System.out.println("************************ 总统紫云英蜂蜜 "+proxy.getFromCache("总统紫云英蜂蜜"));
    }

    @Override
    public boolean exist(String key) {
        HCCacheProxy<String, String> proxy = new HCCacheProxy(luceneCache);
        return proxy.getFromCache(key) != null ? true : false;
    }

    public Cache getLuceneCache() {
        return luceneCache;
    }

    public void setLuceneCache(Cache luceneCache) {
        this.luceneCache = luceneCache;
    }

    public DrugDictionaryRepository getDrugRepository() {
        return drugRepository;
    }

    public void setDrugRepository(DrugDictionaryRepository drugRepository) {
        this.drugRepository = drugRepository;
    }
}
