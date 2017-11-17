package com.lance.demo.framework;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import javax.validation.constraints.NotNull;

@SpringBootApplication
@EnableDiscoveryClient
public class Bootstrap {
    public ApplicationContext run(@NotNull Class clazz, String[] args) {
        return new SpringApplicationBuilder(clazz,Bootstrap.class)
                .bannerMode(Banner.Mode.OFF)
                // .listeners(null)
                .initializers(null)
                // .environment(null)
                // .properties(null)
                .registerShutdownHook(true)
                .run(args);
    }
}
