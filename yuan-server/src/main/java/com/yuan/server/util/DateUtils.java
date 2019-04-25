package com.yuan.server.util;

import org.apache.log4j.Logger;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class DateUtils {

    static Logger logger = Logger.getLogger(DateUtils.class);

    static String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String format(Date date)
    {
        return format(date, DEFAULT_FORMAT_PATTERN);
    }

    /**
     * 格式化日期
     * @param date
     * @param str
     * @return
     */
    public static String format(Date date, String str)
    {
        SimpleDateFormat format = new SimpleDateFormat(str);
        try
        {
            return format.format(date);
        }catch (Exception e){
            logger.info(e);
        }
        return null;
    }

    /**
     * 将字符串解析成日期
     * @param target
     * @param pattern
     * @return
     */
    public static Date parse(String target, String pattern)
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(target);
        } catch (Exception e) {
            logger.info(e);
        }
        return null;
    }

    /**
     * 将字符串解析成日期
     * @param target
     * @return
     */
    public static Date parse(String target)
    {
        return parse(target, DEFAULT_FORMAT_PATTERN);
    }

    /**
     * mongo 日期查询isodate
     * @param dateStr
     * @return
     */
    public static Date dateToISODate(String dateStr){
        //T代表后面跟着时间，Z代表UTC统一时间
        Date date = formatD(dateStr);
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        String isoDate = format.format(date);
        try {
            return format.parse(isoDate);
        } catch (ParseException e) {
            logger.info(e);
        }
        return null;
    }
    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Date formatD(String dateStr){
        return formatD(dateStr,DATE_TIME_PATTERN);
    }

    public static Date formatD(String dateStr ,String format)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date ret = null ;
        try {
            ret = simpleDateFormat.parse(dateStr) ;
        } catch (ParseException e) {
            logger.info(e);
        }
        return ret;
    }

    /**
     * 根据年 月 获取当月的天数
     * @param year
     * @param month
     * @return
     */
    public int getMaxDateByYearAndMonth(int year,int month){
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * java8(经测试别的版本获取2月有bug) 获取某月第一天的00:00:00
     * @return
     */
    public static String getFirstDayOfMonth(String datestr){
        if (isBlank(datestr)) return  null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = strToDateNotDD(datestr);
        if(date == null)
        {
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        return sdf.format(dates);
    }
    /**
     * java8(别的版本获取2月有bug) 获取某月最后一天的23:59:59
     * @return
     */
    public static String getLastDayOfMonth(String datestr){
        if (isBlank(datestr)) return  null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = strToDateNotDD(datestr);
        if(date == null)
        {
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        return sdf.format(dates);
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM( 2017-02)
     * @param strDate
     * @return
     */
    public static Date strToDateNotDD(String strDate) {
        if (isBlank(strDate)) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 获取当前日期的月份
     * @return month
     * */
    public static int getNowMonth(){
        Calendar a = Calendar.getInstance();
        a.setTime(new Date());
        return a.get(Calendar.MONTH)+1;
    }
}
