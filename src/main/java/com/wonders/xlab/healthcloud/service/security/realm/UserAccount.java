package com.wonders.xlab.healthcloud.service.security.realm;

import java.util.Collection;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.wonders.xlab.healthcloud.service.security.principal.BasicPrincipal;

public class UserAccount implements Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 第一（主）principal，基础principal */
	private BasicPrincipal basicPrincipal;
	
	// 暂时没有角色roles，permissions
	
	/** realm名字（单个realm） */
	private String realmName;
	/** principal 集合 */
	private SimplePrincipalCollection spc;
	
	/**
	 * 测试用构造函数。
	 * @param realmName
	 */
	public UserAccount(String realmName, String userTel, String userName) {
		super();
		this.realmName = realmName;
		this.spc = new SimplePrincipalCollection();
		
		BasicPrincipal basicPrincipal = new BasicPrincipal(userTel, userName);
		this.spc.add(basicPrincipal, this.realmName);
	}
	
	public BasicPrincipal getBasicPrincipal() {
		return basicPrincipal;
	}
	public void setBasicPrincipal(BasicPrincipal basicPrincipal) {
		this.basicPrincipal = basicPrincipal;
	}

	public String getRealmName() {
		return realmName;
	}
	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}

	public SimplePrincipalCollection getSpc() {
		return spc;
	}
	public void setSpc(SimplePrincipalCollection spc) {
		this.spc = spc;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Permission> getObjectPermissions() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PrincipalCollection getPrincipals() {
		return this.spc;
	}
	@Override
	public Collection<String> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<String> getStringPermissions() {
		// TODO Auto-generated method stub
		return null;
	}
}
