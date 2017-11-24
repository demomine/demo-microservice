package com.lance.demo.microservice.server;

import com.lance.demo.framework.Bootstrap;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class PayBootStrap {
    public static void main(String[] args) {
        Bootstrap.run(PayBootStrap.class, args);
    }
}
