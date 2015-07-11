package com.wonders.xlab.healthcloud.service.security.realm;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * 用户数据库Realm。
 * @author xu
 *
 */
@Component
public class UserDbRealm extends AuthorizingRealm {
	
	// TODO：测试用户
	private Map<UserLoginToken, UserAccount> testUsers = new HashMap<>();
	
	public UserDbRealm() {
		// realm域名
		setName("userDbRealm");
		// 开启cache ,缓存管理器 使用Ehcache实现
		setCachingEnabled(true);
		// 启用session authentication缓存，并设定缓存名
		setAuthenticationCachingEnabled(true);
		setAuthenticationCacheName("myAuthenticationCache");
		// 启用session authorization缓存，并设定缓存名
		setAuthorizationCachingEnabled(true);
		setAuthorizationCacheName("myAuthorizationCache");
		
		// TODO：添加测试用户
		UserLoginToken token1 = new UserLoginToken("userName1", "", false, "127.0.0.1");
		UserLoginToken token2 = new UserLoginToken("userName2", "", false, "127.0.0.1");
		UserLoginToken token3 = new UserLoginToken("userName3", "", false, "127.0.0.1");
		UserAccount userAccount1 = new UserAccount("userDbRealm", "userTel1", "userName1");
		UserAccount userAccount2 = new UserAccount("userDbRealm", "userTel2", "userName2");
		UserAccount userAccount3 = new UserAccount("userDbRealm", "userTel3", "userName3");
		testUsers.put(token1, userAccount1);
		testUsers.put(token2, userAccount2);
		testUsers.put(token3, userAccount3);
		
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO：以后改
		
		UserLoginToken upToken = (UserLoginToken) token;
		String loginname = upToken.getUsername();
		if (loginname == null || "".equals(loginname)) {
			throw new AccountException("用户名必须填写！");
		}
		
		try {
			UserAccount user = this.testUsers.get(upToken);
			if (user == null) {
				throw new UnknownAccountException("登录名为：" + loginname + " 的账户不存在！");
			}
			
			// 返回身份验证信息
			return user;
		} catch (UnknownAccountException exp) {
			throw exp;
		} catch (DataAccessException exp) {
			exp.printStackTrace();
			throw new AuthenticationException("数据访问对象异常！", exp);
		} catch (RuntimeException exp) {
			exp.printStackTrace();
			throw new AuthenticationException("运行时异常！", exp);
		} catch (Exception exp) {
			exp.printStackTrace();
			throw new AuthenticationException("其他错误！", exp);
		}
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// 暂时没有授权信息
		return null;
	}
}
