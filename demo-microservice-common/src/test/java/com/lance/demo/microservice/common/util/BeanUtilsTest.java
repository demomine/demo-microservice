package com.lance.demo.microservice.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanUtilsTest {

    @Test
    public void toMap() {
    }

    @Test
    public void copy() {
        Demo src = new Demo("demo");
        Demo des = new Demo(null);
        BeanUtils.copy(des, src);
        assertEquals("demo",des.name);
    }

    @Data@AllArgsConstructor
    static class Demo{
        private String name;
    }
}