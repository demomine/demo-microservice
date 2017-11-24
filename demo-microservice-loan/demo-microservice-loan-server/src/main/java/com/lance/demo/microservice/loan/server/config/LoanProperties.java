package com.lance.demo.microservice.loan.server.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "loan")
@Data
public class LoanProperties {
    private String status;
}
