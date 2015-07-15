package com.wonders.xlab.healthcloud.service.cache;

import java.util.Map;

/**
 * 缓存。
 *
 * @param <K> 缓存key类型
 * @param <V> 缓存value类型
 * @author xu
 */
public interface HCCache<K, V> {

    /**
     * 添加一个element到缓存。
     */
    void addToCache(K key, V value);

    /**
     * 添加一组element到缓存。
     */
    void addToCache(Map<K, V> map);

    /**
     * 从缓存中删除一个element。
     */
    void removeFromCache(K key);

    /**
     * 从缓存中获取一个element。
     *
     * @return 没有找到返回null。
     */
    V getFromCache(K key);

    V putIfAbsent(K key, V value);

}
