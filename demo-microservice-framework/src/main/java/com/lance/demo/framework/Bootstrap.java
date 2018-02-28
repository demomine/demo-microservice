package com.lance.demo.framework;

import com.google.common.base.Preconditions;
import com.lance.demo.framework.discovery.DiscoveryListener;
// import com.lance.demo.microservice.tracing.TracingListener;
//import com.lance.demo.microservice.tracing.TracingListener;
import com.lance.demo.microservice.tracing.TracingListener;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lance.demo")
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableTurbine
public class Bootstrap {
    public static ApplicationContext run(@NotNull Class clazz, String[] args) {
        preCheck();
        return new SpringApplicationBuilder(clazz,Bootstrap.class)
                .bannerMode(Banner.Mode.OFF)
                .listeners(new DiscoveryListener(),new TracingListener())
                .registerShutdownHook(true)
                .run(args);
    }

    private static void preCheck() {
        // process tag = local
        Preconditions.checkArgument(StringUtils.hasText(System.getProperty(FrameworkConstants.ENV_TAG)), "please re-run application with env [-Dtag]");
        String tag = System.getProperty(FrameworkConstants.ENV_TAG);
        if (tag.equalsIgnoreCase(FrameworkConstants.LOCAL_MODE)) {
            System.setProperty(FrameworkConstants.Consul.CONFIG_ENABLED, Boolean.FALSE.toString());
            System.setProperty(FrameworkConstants.Consul.CONSUL_ENABLED, Boolean.FALSE.toString());
            System.setProperty(FrameworkConstants.Zipkin.ENABLED, Boolean.FALSE.toString());
            System.setProperty(FrameworkConstants.Zipkin.ENABLED, Boolean.FALSE.toString());
            System.setProperty(FrameworkConstants.Bus.ENABLED, Boolean.FALSE.toString());
        }
        System.setProperty(FrameworkConstants.Consul.DISCOVERY_TAGS, System.getProperty(FrameworkConstants.ENV_TAG));
        System.setProperty(FrameworkConstants.Consul.DISCOVERY_QUERY_TAGS, System.getProperty(FrameworkConstants.ENV_TAG));


        //process monitor=disable
        String monitoring = System.getProperty(FrameworkConstants.MONITORING);
        if (StringUtils.hasText(monitoring) && monitoring.equalsIgnoreCase(FrameworkConstants.NO_MONITOR_MODE)) {
            System.setProperty(FrameworkConstants.Monitor.ENABLED, Boolean.FALSE.toString());
        }

    }
}
