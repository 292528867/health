package com.wonders.xlab.healthcloud.service.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置。
 * @author xu
 */
@Configuration
public class EhCacheConfiguration {
    @Bean
    public CacheManager ehcacheManager() {
        // 使用默认 ehcache-failsafe.xml 配置，配置一个单例缓存管理器
        return CacheManager.create();
    }

    @Bean
    public Cache idenCodeCache(CacheManager ehcacheManager) {
        // 创建用户验证编码缓存
        Cache cache = new Cache(
                new CacheConfiguration(
                        "idenCodeCache", // 缓存名
                        5000 // 缓存最大个数
                )
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                .eternal(false) // 对象是否永久有效
                .timeToIdleSeconds(0) // 对象失效前允许的闲置时间， 0，闲置时间无穷大
                .timeToLiveSeconds(20 * 60) // 对象的失效时间，这里设置失效时间 10分钟
                .diskExpiryThreadIntervalSeconds(10) // 10秒间隔检测 idle 和 live状态
                .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)) // 当缓存满了，或者重启时，不持久化数据
        );
        ehcacheManager.addCache(cache); // 必须加入缓存，不要忘了
        return cache;
    }

    @Bean
    public Cache shiroCache(CacheManager ehcacheManager) {
        // 创建shiro安全框架用缓存
        Cache cache = new Cache(
                new CacheConfiguration(
                        "shiroCache", // 缓存名
                        10000 // 缓存最大个数
                )
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                .eternal(false) // 对象是否永久有效
                .timeToIdleSeconds(120) // 对象失效前允许的闲置时间， 0，闲置时间无穷大
                .timeToLiveSeconds(120) // 对象的失效时间，这里设置失效时间 120秒
                .diskExpiryThreadIntervalSeconds(120) // 10秒间隔检测 idle 和 live状态
                .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)) // 当缓存满了，或者重启时，不持久化数据
        );
        ehcacheManager.addCache(cache); // 必须加入缓存，不要忘了
        return cache;
    }
    
    @Bean
    public Cache luceneCache(CacheManager ehcacheManager) {
    	// 创建lucene分词缓存
    	Cache cache = new Cache(
                new CacheConfiguration(
                        "luceneCache", // 缓存名
                        500000 // 缓存最大个数（50万）
                )
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                .eternal(false) // 对象是否永久有效
                .timeToIdleSeconds(0) // 对象失效前允许的闲置时间， 0，闲置时间无穷大
                .timeToLiveSeconds(0) // 对象的失效时间，0，永远有效
                .diskExpiryThreadIntervalSeconds(120) // 10秒间隔检测 idle 和 live状态
                .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)) // 当缓存满了，或者重启时，不持久化数据
        );
        ehcacheManager.addCache(cache); // 必须加入缓存，不要忘了
        return cache;
    }

    @Bean
    public Cache emCache(CacheManager ehcacheManager) {
        // 创建lucene分词缓存
        Cache cache = new Cache(
                new CacheConfiguration(
                        "emCache", // 缓存名
                        6 // 缓存最大个数（6个）
                )
                        .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                        .eternal(false) // 对象是否永久有效
                        .timeToIdleSeconds(0) // 对象失效前允许的闲置时间， 0，闲置时间无穷大
                        .timeToLiveSeconds(6 * 24 * 60 * 60) // 对象的失效时间，0，永远有效
                        .diskExpiryThreadIntervalSeconds(120) // 10秒间隔检测 idle 和 live状态
                        .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)) // 当缓存满了，或者重启时，不持久化数据
        );
        ehcacheManager.addCache(cache); // 必须加入缓存，不要忘了
        return cache;
    }

    /*@Bean
    public Cache questionnaireCache(CacheManager ehcacheManager) {
        // 创建lucene分词缓存
        Cache cache = new Cache(
                new CacheConfiguration(
                        "questionnaireCache", // 缓存名
                        100 // 缓存最大个数（100个）
                )
                        .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                        .eternal(false) // 对象是否永久有效
                        .timeToIdleSeconds(0) // 对象失效前允许的闲置时间， 0，闲置时间无穷大
                        .timeToLiveSeconds(7 * 24 * 60 * 60) // 对象的失效时间，0，永远有效
                        .diskExpiryThreadIntervalSeconds(120) // 10秒间隔检测 idle 和 live状态
                        .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)) // 当缓存满了，或者重启时，不持久化数据
        );
        ehcacheManager.addCache(cache); // 必须加入缓存，不要忘了
        return cache;
    }*/

}