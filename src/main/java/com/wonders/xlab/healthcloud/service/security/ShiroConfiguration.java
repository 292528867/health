package com.wonders.xlab.healthcloud.service.security;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.CacheManager;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.wonders.xlab.healthcloud.service.security.mgt.MySessionListener;
import com.wonders.xlab.healthcloud.service.security.mgt.MyWebSessionManager;
import com.wonders.xlab.healthcloud.service.security.realm.UserDbRealm;

/**
 * Shiro安全框架配置。
 * @author xu
 *
 */
@Configuration
public class ShiroConfiguration {
	
	/**
	 * BeanPostProcessor是spring容器的一个扩展点，
	 * shiro的这个扩展，用于自动执行init()方法，destory()方法，
	 * init() 是 org.apache.shiro.util.Initializable 接口实现方法
	 * destory() 是 org.apache.shiro.util.Destroyable 接口实现方法
	 * @return
	 */
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	/**
	 * 自定义 shiro EhCacheManager。
	 * ehcache从2.5开始只允许一个jvm一个cachemanager实例，shiro默认创建一个，
	 * 因为hibernate也用了，所以这边要确保只有一个， 使用自定义的ehcacheManager
	 */
	@Bean(name = "shiroEhcacheManager")
	public EhCacheManager getShiroEhCacheManager(CacheManager ehcacheManager) {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManager(ehcacheManager);
		return em;
	}
	
	/**
	 * 权限realm。
	 * @return
	 */
	@Bean(name = "userDbRealm")
	@DependsOn("lifecycleBeanPostProcessor")
	public UserDbRealm getUserDbRealm() {
		final UserDbRealm realm = new UserDbRealm();
		return realm;
	}
	
	/**
	 * 安全管理器，使用自定义WebSessionManager，{@link MyWebSessionManager}。
	 * @param shiroEhcacheManager 缓存管理器，注入
	 * @param userDbRealm 用户realm，注入
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(EhCacheManager shiroEhcacheManager, UserDbRealm userDbRealm) {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		// 使用自定义的WebSessionManager注入
		MyWebSessionManager msm = new MyWebSessionManager();
		dwsm.setSessionManager(msm);
		// 使用ehcache缓存session dao，以后可能再定义数据库session dao
		msm.setSessionDAO(new EnterpriseCacheSessionDAO());
		// 取消session失效检测机制，由后台控制，invalid session
		msm.setSessionValidationSchedulerEnabled(false);
		// 定义session监听器
		List<SessionListener> sessionListeners = new ArrayList<>();
		sessionListeners.add(new MySessionListener());
		msm.setSessionListeners(sessionListeners);
		// 设置realm
		dwsm.setRealm(userDbRealm);
		// 设置缓存控制器
		dwsm.setCacheManager(shiroEhcacheManager);
		
		
		// TODO：配置filter
		
		
		return dwsm;
	}
	
	
	
	
	
	
	
}
