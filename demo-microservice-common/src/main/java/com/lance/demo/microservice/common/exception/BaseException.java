package com.lance.demo.microservice.common.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseException extends RuntimeException implements Serializable {

    private String key;
    private String[] args;
    private boolean logError = true;

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String key, String... args) {
        this.key = key;
        this.args = args;
    }

    public BaseException(String key, boolean logError, String... args) {
        this.key = key;
        this.args = args;
        this.logError = logError;
    }

    public static BaseException of(String key, String... args) {
        return of(key, true, args);
    }

    public static BaseException of(String key, boolean logError, String... args) {
        return new BaseException(key, logError, args);
    }

    public String getMessage() {
        return this.logError + ":" + this.key;
    }
}
