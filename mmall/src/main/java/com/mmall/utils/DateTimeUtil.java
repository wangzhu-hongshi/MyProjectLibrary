package com.mmall.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间格式转化
 * 毫秒转化为时间
 *
 */
public class DateTimeUtil {
    //joda-time
    //str-date 毫秒转化为时间
    //date-str 时间转化为毫秒
    public static final String STANDARD_FORMAT="yyy-MM-dd HH:mm:ss";//转化的格式

    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(formatStr);//格式
        DateTime dateTime=dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
    public static String dateToStr(Date date,String formatStr){
        if(date==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(formatStr);
    }
    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(STANDARD_FORMAT);//格式
        DateTime dateTime=dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
    public static String dateToStr(Date date){
        if(date==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    public static void main(String[] args) {
        System.out.println(DateTimeUtil.dateToStr(new Date(),"yyy-MM-dd HH:mm:ss"));
        System.out.println(DateTimeUtil.strToDate("2010-01-01 11:11:11","yyy-MM-dd HH:mm:ss"));
    }
}
