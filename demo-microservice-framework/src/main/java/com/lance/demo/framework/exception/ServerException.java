package com.lance.demo.framework.exception;

import lombok.Data;

@Data
public class ServerException extends RuntimeException {
    private String description;
    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, String description) {
        super(message);
        this.description = description;
    }

}
