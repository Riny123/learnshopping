package com.neuedu.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtil {

    //定义一个默认的时间格式
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将date转成字符串
     */
    public static String dateToStr(Date date,String format){
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }

    //方法的重载
    public static String dateToStr(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    /**
     * 将字符串的时间转化为date
     */
    public static Date strToDate(String str){
        DateTimeFormatter dtf = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dtf.parseDateTime(str);
        return dateTime.toDate();
    }

    public static Date strToDate(String str,String formart){
        DateTimeFormatter dtf = DateTimeFormat.forPattern(formart);
        DateTime dateTime = dtf.parseDateTime(str);
        return dateTime.toDate();
    }

}
