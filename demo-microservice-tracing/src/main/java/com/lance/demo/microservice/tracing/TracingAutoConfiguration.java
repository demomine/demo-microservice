package com.lance.demo.microservice.tracing;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

@EnableCircuitBreaker
@EnableHystrixDashboard
@Configuration
public class TracingAutoConfiguration {

}
