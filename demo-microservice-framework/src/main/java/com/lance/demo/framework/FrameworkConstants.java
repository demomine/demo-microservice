package com.lance.demo.framework;

public interface FrameworkConstants {
    String ENV_TAG = "tag";
    String MONITORING = "monitor";
    String LOCAL_MODE = "local";
    String NO_MONITOR_MODE = "disable";
    String BOOTSTRAP_LOCATION = "spring.cloud.bootstrap.location";

    interface Consul {
        String ENABLED = "spring.cloud.consul.enabled";
        String CONFIG_ENABLED = "spring.cloud.consul.config.enabled";
        String DISCOVERY_TAGS = "spring.cloud.consul.discovery.tags";
        String DISCOVERY_QUERY_TAGS = "spring.cloud.consul.discovery.default-query-tag";
    }

    interface Zipkin {
        String ENABLED = "spring.zipkin.enabled";
    }

    interface Monitor {
        String ENABLED = "spring.boot.admin.client.enabled";
    }

    interface Bus {
        String ENABLED = "spring.cloud.bus.enabled";

    }
}
