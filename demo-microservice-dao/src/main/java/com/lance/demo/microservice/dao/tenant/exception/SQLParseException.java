package com.lance.demo.microservice.dao.tenant.exception;


public class SQLParseException extends RuntimeException {

    public SQLParseException(String message){
        super(message);
    }

}
