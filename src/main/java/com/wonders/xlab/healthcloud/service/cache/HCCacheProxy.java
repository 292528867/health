package com.wonders.xlab.healthcloud.service.cache;

import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 缓存代理类，用于代理具体的缓存实现，方便以后切换缓存实现。
 * @author xu
 *
 * @param <KEYTYPE>
 * @param <VALUETYPE>
 */
public class HCCacheProxy<KEYTYPE, VALUETYPE> implements HCCache<KEYTYPE, VALUETYPE>{
	/** ehCache缓存实现 */
	private Cache ehCache;
	public HCCacheProxy(Cache cache) {
		this.ehCache = cache;
	}
	
	@Override
	public void addToCache(KEYTYPE key, VALUETYPE value) {
		Element element =  new Element(key, value);
		this.ehCache.put(element);
	}

	@Override
	public void addToCache(Map<KEYTYPE, VALUETYPE> map) {
		for (KEYTYPE key : map.keySet()) 
			this.addToCache(key, map.get(key));
	}

	@Override
	public void removeFromCache(KEYTYPE key) {
		this.ehCache.remove(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public VALUETYPE getFromCache(KEYTYPE key) {
		Element element = this.ehCache.get(key);
		if (element != null) 
			return (VALUETYPE) element.getObjectValue();
		else
			return null;
	}

	@Override
	public VALUETYPE putIfAbsent(KEYTYPE key, VALUETYPE value){
		Element element = new Element(key, value);
		return (VALUETYPE)ehCache.putIfAbsent(element).getObjectValue();
	}
	
}
