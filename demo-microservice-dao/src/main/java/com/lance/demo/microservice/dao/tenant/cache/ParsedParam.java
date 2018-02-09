package com.lance.demo.microservice.dao.tenant.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class ParsedParam<T> {

    private String param;
    private T value;
    private Class<T> javaType;
    private int position;

    /**
     * when insert, add = false
     */
    private boolean add = true;

    public ParsedParam(String param, T value, Class<T> javaType, int position) {
        this(param, value, javaType, position, true);
    }
}
