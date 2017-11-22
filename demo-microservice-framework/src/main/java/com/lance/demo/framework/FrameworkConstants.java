package com.lance.demo.framework;

public interface FrameworkConstants {
    String ENV_TAG = "tag";
    String LOCAL_MODE = "local";
    String BOOTSTRAP_LOCATION = "spring.cloud.bootstrap.location";

    interface Consul {
        String ENABLED = "spring.cloud.consul.enabled";
        String CONFIG_ENABLED = "spring.cloud.consul.config.enabled";
    }
}
