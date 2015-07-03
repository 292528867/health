package com.wonders.xlab.healthcloud.service.security.realm;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.shiro.authc.UsernamePasswordToken;

public class UserLoginToken extends UsernamePasswordToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserLoginToken(final String username, final String password, final boolean rememberMe, final String host) {
		super(username, password, rememberMe, host);
	}
	
	@Override
	public Object getPrincipal() {
		// 这个是key值，默认返回用户名，以后要改
		return super.getPrincipal();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof UserLoginToken))
			return false;
		UserLoginToken castOther = (UserLoginToken) other;
		return new EqualsBuilder()
			.append(this.getUsername(), castOther.getUsername())
			.append(this.getPassword(), castOther.getPassword())
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(this.getUsername())
			.append(this.getPassword())
			.hashCode();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
