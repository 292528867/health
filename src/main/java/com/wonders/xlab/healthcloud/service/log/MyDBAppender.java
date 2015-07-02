package com.wonders.xlab.healthcloud.service.log;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;

/**
 * 自定义logback数据库日志appender。
 * @author xu
 *
 */
public class MyDBAppender extends DBAppenderBase<ILoggingEvent> {

	@Override
	protected Method getGeneratedKeysMethod() {
		// 暂时不用数据源的 getGeneratedKeys 功能，没搞明白这个意思
		return null;
	}

	@Override
	protected String getInsertSQL() {
		return "insert into hc_log_info(MESSAGE,LEVEL,LOGTIME) values(?,?,?) ";
	}

	@Override
	protected void subAppend(ILoggingEvent eventObject, Connection connection,
			PreparedStatement statement) throws Throwable {
		// 设定statement，以后这里可以再定制
		statement.setString(1, eventObject.getFormattedMessage());
		statement.setString(2, eventObject.getLevel().toString());
		statement.setTimestamp(3, new Timestamp(eventObject.getTimeStamp()));
		
		// 插入数据
		int updateCount = 0;
		try {
			updateCount = statement.executeUpdate();
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		}
		
		if (updateCount != 1) 
			addWarn("Failed to insert loggingEvent");
	}

	@Override
	protected void secondarySubAppend(ILoggingEvent eventObject,
			Connection connection, long eventId) throws Throwable {
		// 不保存properties的信息，因为使用的是自己的表，暂时不用了
	}
	
}
