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
    	CacheManager cm = CacheManager.create();
    	
    	// 定义shiro内部使用的缓存，这部分缓存暂时不需要暴露到外部（以bean的形式）
    	// 1、创建shiroCache缓存，这个貌似是全局的
    	Cache shiroCache = new Cache(
                new CacheConfiguration(
                        "shiroCache", // 缓存名
                        1000 // 缓存最大个数
                )
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                .eternal(false) // 对象是否永久有效
                .timeToIdleSeconds(120) // 对象失效前允许的闲置时间， 0，闲置时间无穷大
                .timeToLiveSeconds(120) // 对象的失效时间，这里设置失效时间 120秒
                .diskExpiryThreadIntervalSeconds(120) // 10秒间隔检测 idle 和 live状态
                .persistence(new PersistenceConfiguration().strategy(Strategy.NONE)) // 当缓存满了，或者重启时，不持久化数据
        );
        cm.addCache(shiroCache); // 必须加入缓存，不要忘了
        // 2、创建authentication缓存，在自定义的realm中名字定义为myAuthenticationCache
        Cache myAuthenticationCache = new Cache(
                new CacheConfiguration(
                        "myAuthenticationCache", // 这个是默认session的缓存
                        10000 // 缓存最大个数
                )
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                .eternal(true) // 对象永久有效
                .persistence(new PersistenceConfiguration().strategy(Strategy.NONE)) // 当缓存满了，或者重启时，不持久化数据
        );
        cm.addCache(myAuthenticationCache); // 必须加入缓存，不要忘了
        // 3、创建authorization缓存，在自定义的realm中名字定义为myAuthorizationCache
        Cache myAuthorizationCache = new Cache(
                new CacheConfiguration(
                        "myAuthorizationCache", // 这个是默认session的缓存
                        10000 // 缓存最大个数
                )
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                .eternal(true) // 对象永久有效
                .persistence(new PersistenceConfiguration().strategy(Strategy.NONE)) // 当缓存满了，或者重启时，不持久化数据
        );
        cm.addCache(myAuthorizationCache); // 必须加入缓存，不要忘了
        // 4、创建session缓存，默认名字定义为shiro-activeSessionCache
        Cache shiro_activeSessionCache = new Cache(
                new CacheConfiguration(
                        "shiro-activeSessionCache", // 这个是默认session的缓存
                        10000 // 缓存最大个数
                )
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                .eternal(true) // 对象是否永久有效
                .persistence(new PersistenceConfiguration().strategy(Strategy.NONE)) // 当缓存满了，或者重启时，持久化数据到磁盘
                .maxEntriesLocalDisk(0) // 磁盘中最大缓存对象数，0表示无限大
        );
        cm.addCache(shiro_activeSessionCache); // 必须加入缓存，不要忘了
    	
        return cm;
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
    public Cache discoveryCache(CacheManager ehcacheManager) {
        // discovery缓存
    	// 每个用户每天一条缓存，一天后失效
        Cache cache = new Cache(
                new CacheConfiguration(
                        "discoveryCache", // 缓存名
                        5000 // 缓存最大个数
                )
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                .eternal(false) // 对象是否永久有效
                .timeToIdleSeconds(0) // 对象失效前允许的闲置时间， 0，闲置时间无穷大
                .timeToLiveSeconds(24 * 60 * 60) // 对象的失效时间，这里设置失效时间1天
                .diskExpiryThreadIntervalSeconds(10) // 10秒间隔检测 idle 和 live状态
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

    @Bean
    public Cache userQuestionCache(CacheManager ehcacheManager) {
        // 创建用户提问属性缓存 说明：key中字母均为大写，userId为变量
        // key: String              value: String
        // {userId}_ASK_TIME          时间戳
        // {userId}_RESPONDENT        应答人员的ID
        // {userId}_RESPONDENT_TYPE   应答人员的类型
        Cache cache = new Cache(
                new CacheConfiguration(
                        "userQuestionCache", // 缓存名
                        600000 // 缓存最大个数
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
    public Cache questionOrderCache(CacheManager ehcacheManager){
        // 创建用户问题订单缓存，用于遍历检查应答时间是否超时
        // key:String   value:String
        // {userId}       question order id
        Cache cache = new Cache(
                new CacheConfiguration(
                        "questionOrderCache", // 缓存名
                        200000 // 缓存最大个数
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

}