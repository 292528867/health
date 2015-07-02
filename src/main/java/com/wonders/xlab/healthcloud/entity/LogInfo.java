package com.wonders.xlab.healthcloud.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.wonders.xlab.healthcloud.service.log.MyDBAppender;

/**
 * 日志信息，和{@link MyDBAppender}对应。
 * @author xu
 *
 */
@Entity
@Table(name = "BIZ_LOG_INFO")
public class LogInfo extends AbstractPersistable<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 日志消息（格式化的消息） */
	private String message;
	/** 日志级别 */
	private String level;
	/** 日志时间 */
	private Date logtime;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Date getLogtime() {
		return logtime;
	}
	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
	
}
