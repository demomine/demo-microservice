package com.lance.demo.microservice.common.util;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class DateUtilsTest {



    @Test
    public void now() {
        assertNotNull(DateUtils.now());
    }

    @Test
    public void parseLocalDate() {
        LocalDateTime localDateTime = DateUtils.parseLocalDateTime("2017-11-11 13:30:20", DateUtils.STANDARD_DATE_DASH);
        String datetime = DateUtils.format(localDateTime, DateUtils.STANDARD_DATE_DASH);
        String date = DateUtils.format(localDateTime.toLocalDate(), DateUtils.DATE_FORMAT);
        String time = DateUtils.format(localDateTime.toLocalTime(), DateUtils.TIME_FORMAT);
        assertEquals("2017-11-11 13:30:20",datetime);
        assertEquals("2017-11-11",date);
        assertEquals("13:30:20",time);
    }

    @Test
    public void parseLocalTime() {
    }

    @Test
    public void parseLocalDateTime() {
    }

    @Test
    public void parseToDate() {
    }

    @Test
    public void format() {
    }

    @Test
    public void now1() {
    }

    @Test
    public void toLocalDateTime() {
    }

    @Test
    public void toLocalDate() {
    }

    @Test
    public void toLocalTime() {
    }
}