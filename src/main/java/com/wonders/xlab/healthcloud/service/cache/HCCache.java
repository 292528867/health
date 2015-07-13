package com.wonders.xlab.healthcloud.service.cache;

import java.util.Map;

/**
 * 缓存。
 * @author xu
 *
 * @param <KEYTYPE> 缓存key类型
 * @param <VALUETYPE> 缓存value类型
 */
public interface HCCache<KEYTYPE, VALUETYPE> {
	/**
	 * 添加一个element到缓存。
	 */
	public void addToCache(KEYTYPE key, VALUETYPE value);
	/**
	 * 添加一组element到缓存。
	 */
	public void addToCache(Map<KEYTYPE, VALUETYPE> map);
	/**
	 * 从缓存中删除一个element。
	 */
	public void removeFromCache(KEYTYPE key);
	/**
	 * 从缓存中获取一个element。
	 * @return 没有找到返回null。
	 */
	public VALUETYPE getFromCache(KEYTYPE key);

	VALUETYPE putIfAbsent(KEYTYPE key, VALUETYPE value);
}
