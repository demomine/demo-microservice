package com.lance.demo.framework.discovery.consul;

import com.lance.demo.framework.discovery.DiscoveryConstants;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.cloud.bootstrap.BootstrapApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;



public class ConsulListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {
    public static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 6;
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();


        MutablePropertySources propertySources = environment.getPropertySources();
        for (PropertySource<?> source : propertySources) {
            if (source.getName().equals(BootstrapApplicationListener.DEFAULT_PROPERTIES)) {
                if (source instanceof MapPropertySource) {
                    MapPropertySource mapPropertySource = (MapPropertySource) source;
                    String[] propertyNames = mapPropertySource.getPropertyNames();
                    for (String propertyName : propertyNames) {
                        if (propertyName.startsWith(DiscoveryConstants.DISCOVERY_PREFIX)) {
                            String replacer = propertyName.replaceFirst(DiscoveryConstants.DISCOVERY_PREFIX, DiscoveryConstants.CONSUL_PREFIX);
                            mapPropertySource.getSource().put(replacer, mapPropertySource.getProperty(propertyName));
                        }
                    }
                }
            }

        }

    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
