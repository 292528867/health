package com.wonders.xlab.healthcloud.service.cache;

/**
 * Created by wukai on 15/7/3.
 */
public interface LuceneCacheService {
    /**
     * 初始化药品名缓存
     */
    void initLuceneCache();

    /**
     * 判断名称在缓存中是否存在
     * @param key
     * @return
     */
    boolean exist(String key);
}
