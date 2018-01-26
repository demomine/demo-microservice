package com.lance.demo.microservice.loan.server;

import com.lance.demo.framework.Bootstrap;
import com.lance.demo.microservice.loan.server.config.LoanProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableConfigurationProperties(LoanProperties.class)
@EnableTransactionManagement
@ComponentScan("com.lance.demo.microservice.loan")
public class LoanBootStrap {
    public static void main(String[] args) {
        Bootstrap.run(LoanBootStrap.class, args);
    }
}
