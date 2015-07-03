package com.wonders.xlab.healthcloud.service.security.principal;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 基础Principal身份。
 * @author xu
 *
 */
public class BasicPrincipal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 用户手机 */
	private String userTel;
	/** 用户姓名  */
	private String userName;
	
	
	public BasicPrincipal() {
		super();
	}

	public BasicPrincipal(String userTel, String userName) {
		super();
		this.userTel = userTel;
		this.userName = userName;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof BasicPrincipal))
			return false;
		BasicPrincipal castOther = (BasicPrincipal) other;
		return new EqualsBuilder()
			.append(this.userTel, castOther.userTel)
			.append(this.userName, castOther.userName)
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(userTel)
			.append(userName)
			.toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append(userTel)
			.append(userName)
			.toString();
	}
}
