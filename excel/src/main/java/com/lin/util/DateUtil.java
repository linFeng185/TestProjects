package com.lin.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: lin
 * @Date: 2021/2/5 10:09
 */
public class DateUtil {

    public static final String YYYY_MM_DD="yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM="yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";

    /**
     * 日期转字符串，默认格式为 yyyy-MM-dd
     * @param date 传入日期
     * @return 返回字符串
     */
    public static String localDateToStr(LocalDate date){
        return date.toString();
    }

    /**
     * 根据传入格式转换日期字符串
     * @param date
     * @param dateFormat
     * @return
     */
    public static String localDateToStr(LocalDate date,String dateFormat){
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * 日期时间转字符串，默认格式为 yyyy-MM-dd HH:mm
     * @param dateTime
     * @return
     */
    public static String localDateTimeToStr(LocalDateTime dateTime){
        return localDateTimeToStr(dateTime,YYYY_MM_DD_HH_MM);
    }

    /**
     * 根据传入格式转换日期时间字符串
     * @param dateTime
     * @param dateTimeFormat
     * @return
     */
    public static String localDateTimeToStr(LocalDateTime dateTime,String dateTimeFormat){
        return dateTime.format(DateTimeFormatter.ofPattern(dateTimeFormat));
    }

    /**
     * Date类型转LocalDate类型
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * Date类型转LocalDateTime类型
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static LocalDate strToLocalDate(String date){
        return strToLocalDate(date,YYYY_MM_DD);
    }

    public static LocalDate strToLocalDate(String date,String format){
        return LocalDate.parse(date,DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime strToLocalDateTime(String date){
        return strToLocalDateTime(date,YYYY_MM_DD_HH_MM);
    }

    public static LocalDateTime strToLocalDateTime(String date,String format){
        return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(format));
    }
}
