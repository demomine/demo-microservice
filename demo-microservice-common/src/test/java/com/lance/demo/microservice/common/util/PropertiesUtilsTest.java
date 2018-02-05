package com.lance.demo.microservice.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesUtilsTest {

    @Test
    public void load() {
        String value = PropertiesUtils.load("/test.properties").getProperty("test");
        assertEquals("demo",value);
        String value2 = PropertiesUtils.load("test.properties").getProperty("test");
        assertEquals("demo",value2);
    }
}