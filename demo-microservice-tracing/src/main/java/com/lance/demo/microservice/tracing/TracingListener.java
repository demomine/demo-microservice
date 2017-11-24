package com.lance.demo.microservice.tracing;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TracingListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {
    private static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 4;
    @Override
    public void onApplicationEvent( ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        addDiscoveryProperties(environment);
        changeBootstrapProperties(environment);
    }

    private void changeBootstrapProperties(ConfigurableEnvironment environment) {
        try {
            Properties bootProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(TracingConstants.TRACING_LOCATION));
            Set<Map.Entry<Object, Object>> entries = bootProperties.entrySet();
            Properties properties = new Properties();
            for (Map.Entry<Object, Object> entry : entries) {
                String key = String.valueOf(entry.getKey());
                Object value = entry.getValue();
                if (key.startsWith(TracingConstants.TRACING_PREFIX)) {
                    String replacer = key.replaceFirst(TracingConstants.TRACING_PREFIX, TracingConstants.TRACING_REPLACER);
                    properties.put(replacer, value);
                } else if(key.startsWith(TracingConstants.MONITOR_REFIX)){
                    String replacer = key.replaceFirst(TracingConstants.MONITOR_REFIX,TracingConstants.MONITOR_REPLACER);
                    properties.put(replacer, value);
                } else{
                    properties.put(key, value);
                }
            }
            PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(TracingConstants.TRACING_NAME,properties);
            environment.getPropertySources().addLast(propertiesPropertySource);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDiscoveryProperties(ConfigurableEnvironment environment) {
        try {
            Properties discoveryProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(TracingConstants.TRACING_LOCATION));
            PropertiesPropertySource mapPropertySource = new PropertiesPropertySource(TracingConstants.TRACING_NAME, discoveryProperties);
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
