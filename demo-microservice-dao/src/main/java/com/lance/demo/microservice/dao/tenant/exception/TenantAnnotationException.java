package com.lance.demo.microservice.dao.tenant.exception;


public class TenantAnnotationException extends RuntimeException {

    public TenantAnnotationException(String message){
        super(message);
    }

}
