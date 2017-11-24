package com.lance.demo.microservice.loan.server;

import com.lance.demo.framework.Bootstrap;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class LoanBootStrap {
    public static void main(String[] args) {
        Bootstrap.run(LoanBootStrap.class, args);
    }
}
