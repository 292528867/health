package com.wonders.xlab.healthcloud.utils;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
     * @param fromDate 起始时间
     * @param toDate 结束时间
	 * @return 时间间隔（天数）
	 */
	public static int calculatePeiorDaysOfTwoDate(Date fromDate, Date toDate) {
		Period period = new Period(new DateTime(fromDate), new DateTime(toDate), PeriodType.days());
		return period.getDays();
	}

	public static int calculateDaysOfTwoDateIgnoreHours(Date former, Date latter){
		Calendar formerCalendar = Calendar.getInstance();
		Calendar latterCalendar = Calendar.getInstance();
		formerCalendar.setTime(former);
		latterCalendar.setTime(latter);
		formerCalendar.set(
				formerCalendar.get(Calendar.YEAR),
				formerCalendar.get(Calendar.MONTH),
				formerCalendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0
		);
		latterCalendar.set(
				latterCalendar.get(Calendar.YEAR),
				latterCalendar.get(Calendar.MONTH),
				latterCalendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0
		);

		return calculatePeiorDaysOfTwoDate(formerCalendar.getTime(), latterCalendar.getTime());

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

	/**
	 * 判断当前时间是星期几
	 */
	public static int calculateTodayForWeek(){
		Calendar c = Calendar.getInstance();
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	public static String calculateTodayForWeek(Date date,int num){

		String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, num);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(week_index<0){
			week_index = 0;
		}
		return weeks[week_index];
	}

	/**
	 * 获取所有的节假日
	 * @return
	 */
	public static List getAlLHoliday() {
		List list = new ArrayList();
        String path = DateUtils.class.getClassLoader().getResource("holiday.txt").getPath();
        File file = new File(path);
		if (file.isFile() && file.exists()) {
			try {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(isr);
				String holiday;
				while ((holiday = bufferedReader.readLine()) != null) {
					list.add(holiday);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

    public static void main(String[] args) {
        try {
            int days = calculatePeiorDaysOfTwoDate(
                    org.apache.commons.lang3.time.DateUtils.parseDate("2015-7-8", "yyyy-MM-dd"),
                    org.apache.commons.lang3.time.DateUtils.parseDate("2015-8-8", "yyyy-MM-dd")
            );
            System.out.println("days = " + days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
