package com.lance.demo.gate.config;

import com.lance.demo.gate.exception.ExceptionFilter;
import com.lance.demo.gate.trace.TracePostZuulFilter;
import com.lance.demo.gate.trace.TracePreZuulFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@EnableZuulProxy
@Configuration
@EnableAutoConfiguration
public class GatewayConfig {
    @Bean
    public ZuulFilter exceptionFilter() {
        return new ExceptionFilter();
    }

    @Bean
    public ZuulFilter tracePreZuulFilter() {
        return new TracePreZuulFilter();
    }

    @Bean
    public ZuulFilter tracePostZuulFilter() {
        return new TracePostZuulFilter();
    }
}
