package com.lance.demo.framework;

import com.google.common.base.Preconditions;
import com.lance.demo.framework.discovery.DiscoveryListener;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import javax.validation.constraints.NotNull;

@SpringBootApplication
@EnableDiscoveryClient
public class Bootstrap {
    public static ApplicationContext run(@NotNull Class clazz, String[] args) {
        preCheck();
        return new SpringApplicationBuilder(clazz,Bootstrap.class)
                .bannerMode(Banner.Mode.OFF)
                .listeners(new DiscoveryListener())
                .registerShutdownHook(true)
                .run(args);
    }

    private static void preCheck() {
        Preconditions.checkArgument(StringUtils.isNotBlank(System.getProperty(FrameworkConstants.ENV_TAG)), "please re-run application with env [-Dtag]");
        String property = System.getProperty(FrameworkConstants.ENV_TAG);
        if (property.equalsIgnoreCase(FrameworkConstants.LOCAL_MODE)) {
            System.setProperty(FrameworkConstants.Consul.CONFIG_ENABLED, "false");
            // System.setProperty("spring.cloud.discovery.config.enabled", "false");
        }
        /*if (System.getProperty(FrameworkConstants.BOOTSTRAP_LOCATION) == null) {
            System.setProperty(FrameworkConstants.BOOTSTRAP_LOCATION, "classpath:discovery.properties");
        }*/
    }
}
