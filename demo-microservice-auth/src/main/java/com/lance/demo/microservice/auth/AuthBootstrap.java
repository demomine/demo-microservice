package com.lance.demo.microservice.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@SpringBootApplication
public class AuthBootstrap {
    @Value("${test.key}")
    private String value;
    public static void main(String[] args) {
        SpringApplication.run(AuthBootstrap.class, args);
    }

    @GetMapping("/vault")
    public String vault() {
        return value;
    }

}
