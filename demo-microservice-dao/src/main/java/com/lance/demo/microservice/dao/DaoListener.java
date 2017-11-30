package com.lance.demo.microservice.dao;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class DaoListener  implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {
    private static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 4;

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        processSystemProperties(environment);
        changeBootstrapProperties(environment);
        addDiscoveryProperties(environment);
    }

    private void addDiscoveryProperties(ConfigurableEnvironment environment) {

    }

    private void changeBootstrapProperties(ConfigurableEnvironment environment) {

    }

    private void processSystemProperties(ConfigurableEnvironment environment) {

    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
