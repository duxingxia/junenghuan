package com.makeonline.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;


public class TimeUtil {




	/**
	 * The UTC time zone (often referred to as GMT).
	 */
	public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
	/**
	 * Number of milliseconds in a standard second.
	 * 
	 * @since 2.1
	 */
	public static final long MILLIS_PER_SECOND = 1000;
	/**
	 * Number of milliseconds in a standard minute.
	 * 
	 * @since 2.1
	 */
	public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
	/**
	 * Number of milliseconds in a standard hour.
	 * 
	 * @since 2.1
	 */
	public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
	/**
	 * Number of milliseconds in a standard day.
	 * 
	 * @since 2.1
	 */
	public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_DATE = "yyyy-MM-dd";

	public static final String FORMAT_MONTH = "yyyy-MM";

	public static final String FORMAT_DATE_MIN = "yyyy-MM-dd HH:mm";
	public static final String FORMATDATEMIN = "yyyyMMddHHmm";
	public static final String FORMATTIMESTAMP = "yyyyMMddHHmmss";

	public static int defaultNum = 10000;
	public static int logIndex = 10;
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat yyyyMMddhhmmss = new SimpleDateFormat(FORMAT_TIMESTAMP);
	
	public static boolean isBeforeSomeHours(Date now, Date when,
			Double someHours) {
		Double someHoursMs = DateUtils.MILLIS_PER_HOUR * someHours;
		Long aftMs = when.getTime();
		Long befMs = now.getTime();
		return ((aftMs - befMs) > someHoursMs) ? true : false;
	}

	public static int getCurrentHour() {
		Calendar curr = Calendar.getInstance();
		return curr.get(Calendar.HOUR_OF_DAY);
	}

	public static int getHour(Date date) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(date);
		return curr.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * @Title: betweenHours
	 * @Description: beginHour<=currentHour<endHour
	 * @param beginHour
	 * @param currentHour
	 * @param endHour
	 * @return: boolean
	 */
	public static boolean betweenHours(Double beginHour, Double currentHour,
			Double endHour) {
		return ((beginHour <= currentHour) && (currentHour < endHour)) ? true
				: false;
	}

	public static Date getCurrentZeroTime() throws Exception {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		d = sdf.parse(sdf.format(d));
		return d;
	}

	public static long getTimeBeforeDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - days);

		return cal.getTimeInMillis();

	}
	
	public static String getAnyDay(int days) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days);

		return sdf.format(cal.getTime());

	}

	/**
	 * 
	 * @Title: between
	 * @Description: begin<=current<end
	 * @param @param begin
	 * @param @param current
	 * @param @param end
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean between(String begin, String current, String end) {
		int front = current.compareTo(begin);// >=0
		int back = current.compareTo(end);// <0
		int zero = 0;
		return ((zero <= front) && (back < zero)) ? true : false;
	}

	/**
	 * 
	 * @Title: convertTimeToDHM
	 * @Description: 转换时间格式为 x天xx时xx分 入参格式：17:05 07:44 00:18 32:18
	 *               出参格式：x天xx小时xx分 或 xx小时xx分 或 0小时xx分
	 * @param time
	 * @return String 返回类型
	 * @throws
	 */

	public static String convertTimeToDHM(String time) {
		if (matchesTime(time)) {
			String pre = time.split(":")[0];
			String pos = time.split(":")[1];
			int sum = Integer.parseInt(pre);
			int day = sum / 24;
			int hour = sum % 24;
			int minute = Integer.parseInt(pos);

			StringBuffer sb = new StringBuffer();
			if (day > 0) {
				sb.append(day);
				sb.append("天");
			}

			if (hour != 0) {
				sb.append(hour);
				sb.append("小时");
			} else {
				sb.append("0小时");
			}

			if (minute != 0) {
				sb.append(minute);
				sb.append("分");
			} else {
				sb.append("0分");
			}

			return sb.toString();
		}
		return null;
	}

	public static boolean matchesTime(String time) {

		return time.matches("[0-9]+:[0-9]+");
	}

	public static boolean matchesDHM(String dhm) {
		return dhm.matches("[0-9]*\u5C0F\u5929*[0-5]*\u65F6*[0-9]+\u5206*");
	}

	/**
	 * 入参格式：x天xx小时xx分 或 xx小时xx分 或 0小时xx分
	 * 
	 * @param dhm
	 * @return
	 */
	public static String convertDHMToTime(String dhm) {

		int day = 0;
		int hour = 0;
		int minute = 0;
		int hourIndex = 0;
		int dayIndex = 0;
		int minuteIndex = 0;

		if (dhm.contains("天")) {
			day = Integer.parseInt(dhm.split("天")[0]);
			dayIndex = dhm.indexOf("天");
			// System.out.println(dhm.substring(0, dayIndex));
			day = Integer.parseInt(dhm.substring(0, dayIndex));
		}

		if (dhm.contains("小时")) {
			hourIndex = dhm.indexOf("小");
			// System.out.println(dhm.substring(dayIndex+1,hourIndex));
			if (dayIndex == 0) {
				hour = Integer.parseInt(dhm.substring(dayIndex, hourIndex));
			} else {
				hour = Integer.parseInt(dhm.substring(dayIndex + 1, hourIndex));
			}
		}

		if (dhm.contains("分")) {

			minuteIndex = dhm.indexOf("分");
			// System.out.println(dhm.substring(hourIndex+2,minuteIndex));
			minute = Integer
					.parseInt(dhm.substring(hourIndex + 2, minuteIndex));

		}

		return Integer.toString(day * 24 * 60 + hour * 60 + minute);

	}
	

	/**
	 * 入参数格式要求 yyyy-MM-dd hh:mm:ss
	 * 
	 * @param date
	 */
	public static Map<String, String> getDayAndHourMinute(String date) {

		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(date)) {
			return null;
		}

		String yyyyMMdd = date.split("\\s+")[0];
		String yyyy = yyyyMMdd.substring(0, 4);
		String MM = yyyyMMdd.substring(5, 7);
		String dd = yyyyMMdd.substring(8, 10);
		String hhmmss = date.split("\\s+")[1];
		String hhmm = hhmmss.substring(0, 5);

		StringBuffer sb = new StringBuffer();
		// sb.append(yyyy).append("年");
		sb.append(MM).append("月");
		sb.append(dd).append("日");
		map.put("yyyyMMdd", sb.toString());

		sb.setLength(0);
		sb.append(hhmm);

		map.put("hhmm", sb.toString());

		return map;
	}

	/**
	 * 根据传入的日期和格式类型，将Date转化为String类型
	 * 
	 * @param date,format
	 * @return String
	 */
	public static String dateFormatString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 根据传入的格式类型和随机数个数，将当前时间转化为format格式拼接num长度的随机数流水号
	 * 
	 * @param format，num
	 * @return String
	 */
	public static String getTradeNo(String format,int num) {
		Random random = new Random();
		return dateFormatString(new Date(),format) + ((int)Math.pow(10, num)+random.nextInt((int)Math.pow(10, num)));
	}
	
	/**
	 * 根据传入的格式类型，将当前时间转化为format格式拼接自增的5位数字
	 * 
	 * @param format
	 * @return String
	 */
	public static String getTradeNo(String format) {
		return dateFormatString(new Date(),format) + getIncrementNum();
	}
	
	/**
	 * 返回自增的五位数字
	 * 
	 * @return int
	 */
	public synchronized static int getIncrementNum(){
		defaultNum++;
		if(defaultNum > 99999){
			defaultNum = 10000;
		}
		return defaultNum;
	}
	
	/**
	 * 返回自增的2位数字
	 * 
	 * @return int
	 */
	public synchronized static int getLogNum(){
		logIndex++;
		if(logIndex > 100){
			logIndex = 10;
		}
		return logIndex;
	}
	
	/**
	 * 根据传入的时间字符串和格式类型，将String转化为Date类型
	 * 
	 * @param dateString,format
	 * @return Date
	 */
	public static Date stringFormatDate(String dateString, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的时间和格式类型，将timestamp转化为String类型
	 * 
	 * @param timestamp,format
	 * @return String
	 */
	public static String timestampToString(Timestamp timestamp, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(timestamp);
	}

	/**
	 * 根据传入的diffTime值类型（天，时，分，秒），返回相应的结束时间跟起始时间的差值 入参格式：yyyy-MM-dd
	 * HH:mm:ss,yyyy-MM-dd HH:mm:ss,long
	 * 
	 * @param startTime
	 *            ,entTime,diffTime
	 *            diffTime的值为MILLIS_PER_DAY（天），MILLIS_PER_HOUR（
	 *            小时），MILLIS_PER_MINUTE（分钟），MILLIS_PER_SECOND（秒）
	 * @return long
	 */
	public static long diffTime(String startTime, String endTime, long diffTime) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIMESTAMP);
		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(startTime);
			d2 = format.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
//			LoggerUtil.error("解析时间错误", e);
		}
		// 毫秒ms
		long diff = d2.getTime() - d1.getTime();

		return diff / diffTime;
	}

	/**
	 * 根据传入的时间，增加的年数，增加月数，增加天数，返回时间格式 入参格式：String,int,int,int,String
	 * 
	 * @param
	 *            year_num,month_num,day_num,format
	 * @return String
	 */
	public static String addYearMonthDay(String pram_date, int year_num,
			int month_num, int day_num, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar rightNow = Calendar.getInstance();
		Date dt = null;
		try {
			dt = sdf.parse(pram_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, year_num); // 日期加year_num年
		rightNow.add(Calendar.MONTH, month_num); // 日期加month_num个月
		rightNow.add(Calendar.DAY_OF_YEAR, day_num); // 日期加day_num天
		Date right_date = rightNow.getTime();
		return sdf.format(right_date);
	}

    /**
     * 根据传入的时间，增加的小时，增加分钟，增加秒，返回时间格式 入参格式：String,int,int,int,String
     *
     * @param
     *
     * @return String
     */
    public static String addHourMinuteSecond(String pram_date, int hour_num,
                                             int minute_num, int second_num, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar rightNow = Calendar.getInstance();
        Date dt = null;
        try {
            dt = sdf.parse(pram_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.setTime(dt);
        rightNow.add(Calendar.HOUR_OF_DAY, hour_num); // 增加小时
        rightNow.add(Calendar.MINUTE, minute_num); // 增加分钟
        rightNow.add(Calendar.SECOND, second_num); // 增加秒
        Date right_date = rightNow.getTime();
        return sdf.format(right_date);
    }
	public static void main(String[] a) throws Exception {
		System.out.println(getTradeNo(FORMATTIMESTAMP, 5));
//		System.out.println(addYearMonthDay(dateFormatString(new Date(), FORMAT_TIMESTAMP),0,0,59,FORMAT_DATE));

		System.out.println(fromYMDtoYDMHMS("20160712","09:10"));
		
		System.out.println(convertDHMToTime("0小时52分"));
		
		System.out.println(addMinute("2017-12-01 00:00:00","0小时52分"));

	}
	
	/**
	 * 增加指定分钟
	 * @param date
	 * @param minutes
	 */
	public static String addMinute(String date,String duration){
		
		int durationMintue = Integer.parseInt(convertDHMToTime(duration));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sdfDate = null;
		try {
			sdfDate = sdf.parse(date);
		} catch (ParseException e) {
//			LoggerUtil.error("DateUtil.addMinute异常",e);
		}
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdfDate);
        calendar.add(Calendar.MINUTE, durationMintue);
        
        return sdf.format(calendar.getTime());
	}
	
	public static String formateTime(String needToFormat,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		Date date = null;
		try {
			date = sdf.parse(needToFormat);
		} catch (ParseException e) {
//			LoggerUtil.error("解析日期异常", e);
			return needToFormat;
		}
		
		return sdf.format(date);
	}
	
	/**
	 * 相比当前时间小于指定的时间长度
	 * @param
	 * @param
	 * @return
	 */
	public static boolean afterNow(String dateStr,int minutes){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(dateStr);
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MINUTE, minutes);
			
			if( date.compareTo(now.getTime()) > 0){
				return true;
			}
			
		} catch (ParseException e) {
//			LoggerUtil.error("解析日志出错,dateStr="+dateStr+"#hour="+minutes  , e);
			return false;
		}
		return false;
	}
	
	/**
	 * 判定是否大于当前时间
	 * @param time
	 * @return true 目标时间大于当前时间，false 目标时间小于当前时间
	 */
	public static boolean biggerThanNow(String time){
		Date target;
		try {
			target = yyyyMMddhhmmss.parse(time);
			Date nowDate = new Date();
			
			if(target.compareTo(nowDate) >=0){
				return true;
			}
			
		} catch (ParseException e) {
//			LoggerUtil.error("解析时间出错", e);
		}
		return false;
		
	}
	
	/**
	 * 判定是否大于指定时间
	 * @param time
	 * @return true 目标时间大于当前时间，false 目标时间小于当前时间
	 */
	public static boolean biggerThanNow(String time,Date now){
		Date target;
		try {
			target = yyyyMMddhhmmss.parse(time);
			if(target.compareTo(now) >0){
				return true;
			}
			
		} catch (ParseException e) {
//			LoggerUtil.error("解析时间出错", e);
		}
		return false;
		
	}
	
	/**
	 * 判定是否大于当天时间 以天为准，不比较小时分钟秒
	 * @param day 格式 yyyy-mm-dd 
	 * @return true 目标时间大于当前时间，false 目标时间小于当前时间
	 */
	public static boolean biggerThanToday(String someDay){
		Calendar nowCalendar = Calendar.getInstance();
		String today = sdf.format(nowCalendar.getTime());
		
		Date todayDate;
		Date someDate;
		try {
			someDate = sdf.parse(someDay);
			todayDate = sdf.parse(today);
			if(someDate.compareTo(todayDate) >0){
				return true;
			}
			
		} catch (ParseException e) {
//			LoggerUtil.error("解析时间出错", e);
		}
		return false;
		
	}
	
	/**
	 * 判定是否大于当天时间 以天为准，不比较小时分钟秒
	 * @param day 格式 yyyy-mm-dd 
	 * @return true 目标时间大于当前时间，false 目标时间小于当前时间
	 */
	public static boolean biggerEqualThanToday(String someDay){
		Calendar nowCalendar = Calendar.getInstance();
		String today = sdf.format(nowCalendar.getTime());
		
		Date todayDate;
		Date someDate;
		try {
			someDate = sdf.parse(someDay);
			todayDate = sdf.parse(today);
			if(someDate.compareTo(todayDate) >=0){
				return true;
			}
			
		} catch (ParseException e) {
//			LoggerUtil.error("解析时间出错", e);
		}
		return false;
		
	}
	
	/**
	 * 获取系统当前时间，格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentDate(){

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calendar.getTime());
	}




	/**
	 * ydm格式 20160710 hms格式09:10
	 * @param ydm
	 * @param hms
	 * @return
	 * @throws ParseException
	 */
	public static String fromYMDtoYDMHMS(String ydm,String hms){

		String yyyyMMdd = fromYMDtoYDMHMS(ydm);

		StringBuilder sb = new StringBuilder();
		sb.append(yyyyMMdd);
		sb.append(" ");
		sb.append(hms);

		return sb.toString();
	}

	public static String fromYMDtoYDMHMS(String ydm) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(ydm);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			return sdf2.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			LoggerUtil.error("从yyyyMMdd到yyyy-MM-dd转换异常",new RuntimeException());
		}
		return null;
	}

	public static String getNextDay(String cur){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(cur));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
//			LoggerUtil.error("从yyyyMMdd到yyyy-MM-dd转换异常",new RuntimeException());
		}

		return null;

	}
	
	/**
	 * @return  yyyy-mm-dd
	 */
	public static String getCurHHmmdd(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

}

