package com.wonders.xlab.healthcloud.service.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

public class JsonResponseAuthenticationFilter extends AuthenticatingFilter {

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
}
