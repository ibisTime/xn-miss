package com.ogc.standard.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static final String DB_DATE_FORMAT_STRING = "yyyyMMdd";

    public static final String FRONT_DATE_FORMAT_STRING = "yyyy-MM-dd";

    public static final String DATA_TIME_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATA_TIME_PATTERN_2 = "yyyy-MM-dd HH:mm";

    public static final String DATA_TIME_PATTERN_3 = "yyyyMMDDhhmmss";

    public static final String DATA_TIME_PATTERN_4 = "yyyyMMDDhhmmss";

    public static final String DATA_TIME_PATTERN_5 = "yyyyMMddHHmmssSSS";

    public static final String DATA_TIME_PATTERN_6 = "yyyy年MM月dd日";

    public static final String DATA_TIME_PATTERN_7 = "yyyy年MM月dd日 ahh:mm:ss";

    public static final String UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String TIME_BEGIN = " 00:00:00";

    public static final String TIME_MIDDLE = " 12:00:00";

    public static final String TIME_END = " 23:59:59";

    public static Date getStartDatetime(String startDate) {
        Date repayDatetime = DateUtil.strToDate(
            startDate + DateUtil.TIME_BEGIN, DateUtil.DATA_TIME_PATTERN_1);
        return repayDatetime;
    }

    public static Date getEndDatetime(String endDate) {
        Date repayDatetime = DateUtil.strToDate(endDate + DateUtil.TIME_END,
            DateUtil.DATA_TIME_PATTERN_1);
        return repayDatetime;
    }

    public static Date getRelativeDateOfSecond(Date startDate, int second) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(startDate);
            calendar.add(Calendar.SECOND, second);
            return calendar.getTime();
        } catch (Exception e) {
            return startDate;
        }
    }

    public static Date getRelativeDateOfMinute(Date startDate, int minute) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(startDate);
            calendar.add(Calendar.MINUTE, minute);
            return calendar.getTime();
        } catch (Exception e) {
            return startDate;
        }
    }

    public static Date getRelativeDateOfDays(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(startDate);
            calendar.add(Calendar.SECOND, days * 3600 * 24);
            return calendar.getTime();
        } catch (Exception e) {
            return startDate;
        }
    }

    /** 
     * Date按格式pattern转String
     * @param date
     * @param pattern
     * @return 
     * @create: 2015-4-18 下午11:02:34 miyb
     * @history: 
     */
    public static String dateToStr(Date date, String pattern) {
        String str = null;
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        try {
            str = formater.format(date);
        } catch (Exception e) {
        }
        return str;
    }

    /** 
     * 获取当天开始时间
     * @return 
     * @create: 2014-10-14 下午4:24:57 miyb
     * @history: 
     */
    public static Date getTodayStart() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date) currentDate.getTime().clone();
    }

    /** 
     * 获取当天结束时间
     * @return 
     * @create: 2014-10-14 下午4:24:57 miyb
     * @history: 
     */
    public static Date getTodayEnd() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return (Date) currentDate.getTime().clone();
    }

    /**
     * 相对参数today的明日起始时刻。比如今天是11日23点，明日起始时刻为12日0点0分0秒
     * @param today
     * @return 
     * @create: 2015年11月16日 上午11:49:51 myb858
     * @history:
     */
    public static Date getTomorrowStart(Date today) {
        String str = dateToStr(today, FRONT_DATE_FORMAT_STRING);
        Date tommrow = getRelativeDateOfSecond(
            strToDate(str, FRONT_DATE_FORMAT_STRING), 24 * 3600);
        return tommrow;
    }

    /** 
     * String 按格式pattern转Date
     * @param str
     * @param pattern
     * @return 
     * @create: 2015-4-18 下午11:02:34 miyb
     * @history: 
     */
    public static Date strToDate(String str, String pattern) {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        try {
            date = formater.parse(str);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 删除—
     * @param pattern
     * @return 
     * @create: 2015年10月27日 下午7:59:41 myb858
     * @history:
     */
    public static String remove_(String strDate) {
        String string = null;
        try {
            string = strDate.replace("-", "");
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return string;
    }

    /**
     * 按格式获取当前时间
     * @param pattern
     * @return 
     * @create: 2015-5-7 上午11:22:04 miyb
     * @history:
     */
    public static String getToday(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    /**
     * 
     * @param date
     * @param addOneDay 是否加1天
     * @return 
     * @create: 2015-5-7 上午11:25:23 miyb
     * @history:
     */
    public static Date getFrontDate(String date, boolean addOneDay) {
        Date returnDate = null;
        try {
            returnDate = new SimpleDateFormat(FRONT_DATE_FORMAT_STRING)
                .parse(date);
            if (addOneDay) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(returnDate);
                calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
                calendar.add(calendar.SECOND, -1);// 变成23：59：59
                returnDate = calendar.getTime(); // 这个时间就是日期往后推一天的结果
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 统计两个时间差，返回的是天数(即24小时算一天，少于24小时就为0，用这个的时候最好把小时、分钟等去掉)
     * @param beginStr 开始时间
     * @param endStr 结束时间
     * @param format 时间格式
     * @return
     */
    public static int daysBetween(String beginStr, String endStr, String format) {
        Date end = strToDate(endStr, format);
        Date begin = strToDate(beginStr, format);
        long times = end.getTime() - begin.getTime();
        return (int) (times / 60 / 60 / 1000 / 24);
    }

    /**
     * 统计两个时间差，返回的是天数(即24小时算一天，少于24小时就为0，用这个的时候最好把小时、分钟等去掉)
     * @param beginDate
     * @param endDate
     * @return 
     * @create: 2015年11月16日 上午11:20:51 myb858
     * @history:
     */
    public static int daysBetween(Date beginDate, Date endDate) {
        long times = endDate.getTime() - beginDate.getTime();
        return (int) (times / 60 / 60 / 1000 / 24);
    }

    /**
     * 提供格式化时间，统计两个时间差之间返回的天数(即24小时算一天，少于24小时就为0，用这个的时候最好把小时、分钟等去掉)
     * @param beginDate
     * @param endDate
     * @param format
     * @return 
     * @create: 2017年1月8日 下午5:07:50 xieyj
     * @history:
     */
    public static int daysBetweenDate(Date beginDate, Date endDate) {
        String beginStr = DateUtil.dateToStr(beginDate,
            DateUtil.FRONT_DATE_FORMAT_STRING);
        String endStr = DateUtil.dateToStr(endDate,
            DateUtil.FRONT_DATE_FORMAT_STRING);
        Date end = strToDate(endStr, DateUtil.FRONT_DATE_FORMAT_STRING);
        Date begin = strToDate(beginStr, DateUtil.FRONT_DATE_FORMAT_STRING);
        long times = end.getTime() - begin.getTime();
        return (int) (times / 60 / 60 / 1000 / 24);
    }

    public static Date getCurrentMonthFirstDay() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.DATE, 1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date) currentDate.getTime().clone();
    }

    public static Date getCurrentMonthLastDay() {
        Date date = new Date();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        do {
            currentDate.add(Calendar.DATE, 1);
        } while (currentDate.get(Calendar.DATE) != 1);
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return currentDate.getTime();
    }

    public static Date TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
            .format(new java.util.Date(timestamp));
        return strToDate(date, formats);
    }

    /**
     * 得到UTC时间，类型为字符串，格式为"yyyy-MM-dd HH:mm:ss"<br />
     * 如果获取失败，返回null
     * @return
     */
    public static String getUTCTimeStr() {
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        Date utc = new Date(cal.getTimeInMillis());
        return dateToStr(utc, UTC_TIME_FORMAT);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getToday(DateUtil.DB_DATE_FORMAT_STRING));
    }

}
