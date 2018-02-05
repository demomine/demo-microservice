package com.lance.demo.microservice.common.model;

import lombok.Data;

@Data
public class ErrorInfo<T> {
    public static final Integer OK = 0;
    public static final Integer ERROR = 9999;

    private Integer code;
    private String message;
    private String description;
    private T data;
}
