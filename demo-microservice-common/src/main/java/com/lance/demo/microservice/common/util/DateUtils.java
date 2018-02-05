package com.lance.demo.microservice.common.util;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
public class DateUtils {
    public static final ZoneId CHINA_TIME_ZONE = ZoneId.of("Asia/Shanghai");

    public static final String   FULL_DATE           =   "yyyyMMddHHmmssSSSZ";
    public static final String   STANDARD_DATE       =   "yyyyMMddHHmmss";
    public static final String   TIME_FORMAT         =   "HH:mm:ss";
    public static final String   DATE_FORMAT         =   "yyyy-MM-dd";

    public static final String   FULL_DATE_DASH      =   "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String   STANDARD_DATE_DASH  =   "yyyy-MM-dd HH:mm:ss";
    public static final String   TIME_FORMAT_DASH    =   "HHmmss";
    public static final String   DATE_FORMAT_DASH    =   "yyyy-MM-dd";

    public static DateTimeFormatter getDateTimeFormatter(String format) {
        return DateTimeFormatter.ofPattern(format);
    }

    public static String now(String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return ZonedDateTime.now().format(dateTimeFormatter);
    }

    public static LocalDate parseLocalDate(String date,String format) {
        return LocalDate.parse(date, getDateTimeFormatter(format));
    }

    public static LocalTime parseLocalTime(String time, String format) {
        return LocalTime.parse(time, getDateTimeFormatter(format));
    }

    public static LocalDateTime parseLocalDateTime(String datetime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(datetime, dateTimeFormatter);
    }

    public static Date parseToDate(String date,String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    public static String format(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String format(LocalDate localDate, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(dateTimeFormatter);
    }

    public static String format(LocalTime localTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localTime.format(dateTimeFormatter);
    }

    public static String format(Date date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), CHINA_TIME_ZONE);
        return localDateTime.format(dateTimeFormatter);
    }


    public static LocalDateTime plus(LocalDateTime localDateTime, int value, ChronoUnit chronoUnit) {
        return localDateTime.plus(value, chronoUnit);
    }

    public static Date now() {
        return new Date();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), CHINA_TIME_ZONE);
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    public static LocalTime toLocalTime(Date date) {
        return toLocalDateTime(date).toLocalTime();
    }
}
