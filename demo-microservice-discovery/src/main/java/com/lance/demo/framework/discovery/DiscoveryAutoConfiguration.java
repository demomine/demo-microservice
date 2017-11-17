package com.lance.demo.framework.discovery;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClientAutoConfiguration;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

//@EnableDiscoveryClient
//@EnableFeignClients
//@EnableConfigurationProperties(DiscoveryProperties.class)
public class DiscoveryAutoConfiguration extends CompositeDiscoveryClientAutoConfiguration{
}
