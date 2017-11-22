package com.lance.demo.framework.discovery;

import com.lance.demo.framework.discovery.DiscoveryConstants;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.cloud.bootstrap.BootstrapApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.*;

public class DiscoveryListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {
    private static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 4;
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        ConfigurableEnvironment environment = event.getEnvironment();
        processSystemProperties(environment);
        changeBootstrapProperties(environment);
        addDiscoveryProperties(environment);
    }

    private void processSystemProperties(ConfigurableEnvironment environment) {
        Map<String, Object> systemProperties = environment.getSystemProperties();
        Object localTag = systemProperties.get(DiscoveryConstants.TAG_KEY);
        if (localTag != null) {
            String tag = String.valueOf(localTag);
            if (tag.equalsIgnoreCase(DiscoveryConstants.LOCAL_TAG)) {
                systemProperties.put(DiscoveryConstants.CONSUL_ENABLED, Boolean.FALSE.toString());
            }
        }
    }

    private void changeBootstrapProperties(ConfigurableEnvironment environment) {
        try {
            Properties bootProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(DiscoveryConstants.BOOTSTRAP_LOCATION));
            Set<Map.Entry<Object, Object>> entries = bootProperties.entrySet();
            Properties properties = new Properties();
            for (Map.Entry<Object, Object> entry : entries) {
                String key = String.valueOf(entry.getKey());
                Object value = entry.getValue();
                if (key.startsWith(DiscoveryConstants.DISCOVERY_PREFIX)) {
                    String replacer = key.replaceFirst(DiscoveryConstants.DISCOVERY_PREFIX, DiscoveryConstants.CONSUL_PREFIX);
                    properties.put(replacer, value);
                } else {
                    properties.put(key, value);
                }
            }
            PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(DiscoveryConstants.BOOTSTRAP_NAME,properties);
            environment.getPropertySources().addLast(propertiesPropertySource);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDiscoveryProperties(ConfigurableEnvironment environment) {
        try {
            Properties discoveryProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(DiscoveryConstants.DISCOVERY_LOCATION));
            PropertiesPropertySource mapPropertySource = new PropertiesPropertySource(DiscoveryConstants.DISCOVERY_NAME, discoveryProperties);
            environment.getPropertySources().addLast(mapPropertySource);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
