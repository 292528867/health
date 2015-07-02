package com.wonders.xlab.healthcloud.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * @author xu
 *
 */
public class DateUtils {
	/**
	 * 将日期转换成只有年月日的日期，其余默认为0。
	 * @param date 转换用日期
	 * @return yyyy-MM-dd日期
	 */
	public static Date covertToYYYYMMDD(Date date) {
		DateTime tmpTime = new DateTime(date);
		return new DateTime(
			tmpTime.getYear(),
			tmpTime.getMonthOfYear(),
			tmpTime.getDayOfMonth(),
			0, 0, 0
		).toDate();
	}
	
	/**
	 * 返回标准日期格式字符串对应的日期。
	 * @param dateStr 标准日期格式字符串（标准格式：yyyy-MM-dd）
	 * @return yyyy-mm-dd格式对应的日期
	 */
	public static Date covertToYYYYMMDD(String dateStr) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		return fmt.parseDateTime(dateStr).toDate();
	}
	/**
	 * 返回日期的标准字符串描述（标准格式：yyyy-MM-dd）。
	 * @param date 日期
	 * @return 
	 */
	public static String covertToYYYYMMDDStr(Date date) {
		return new DateTime(date).toString("yyyy-MM-dd");
	}
	
	/**
	 * 计算两个日期间隔天数。
	 * @param toDate 日期
	 * @return 出生天数（天数）
	 */
	public static int calculatePeiorDaysOfTwoDate(Date fromDate, Date toDate) {
		Period period = new Period(new DateTime(fromDate), new DateTime(toDate), PeriodType.days());
		return period.getDays();
	}
	/**
	 * 计算两个日期间隔分钟。
	 * @param fromDate 起始时间
	 * @param toDate 结束时间
	 * @return 时间间隔（分钟）
	 */
	public static int calculatePeiorMiniutesOfTwoDate(Date fromDate, Date toDate) {
		Period period = new Period(new DateTime(fromDate), new DateTime(toDate), PeriodType.minutes());
		return period.getMinutes();
	}
}
