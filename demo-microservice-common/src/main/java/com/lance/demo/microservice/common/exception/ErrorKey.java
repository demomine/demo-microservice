package com.lance.demo.microservice.common.exception;

import lombok.Data;

@Data
public class ErrorKey {
    private String key;
    private int status;
    private String message;

    public ErrorKey(String key, int status, String message) {
        this.key = key;
        this.status = status;
        this.message = message;
    }

    public static ErrorKey of(String key) {
        return new ErrorKey(key, 0, null);
    }
}
