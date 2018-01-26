package com.lance.demo.gate.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
@EnableZuulProxy
@Configuration
@EnableAutoConfiguration
public class GatewayConfig {

}
