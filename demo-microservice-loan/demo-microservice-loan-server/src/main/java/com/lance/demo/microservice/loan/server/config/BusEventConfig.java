package com.lance.demo.microservice.loan.server.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RemoteApplicationEventScan("com.lance")
public class BusEventConfig {

    @Bean
    BusListener busListener() {
        return new BusListener();
    }

    @Bean
    MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.lance.demo.microservice");
        return mapperScannerConfigurer;
    }
}
