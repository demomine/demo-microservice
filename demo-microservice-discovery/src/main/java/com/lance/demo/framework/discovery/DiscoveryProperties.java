package com.lance.demo.framework.discovery;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.consul.config.ConsulConfigProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
@ConfigurationProperties(prefix = DiscoveryConstants.DISCOVERY_PREFIX)
public class DiscoveryProperties{

    private ConsulConfigProperties config;

    private ConsulDiscoveryProperties discovery;

    private String host = "localhost";

    private int port = 8500;

    private boolean enabled = true;

    public ConsulConfigProperties getConfig() {
        return config;
    }

    public void setConfig(ConsulConfigProperties config) {
        this.config = config;
    }

    public ConsulDiscoveryProperties getDiscovery() {
        return discovery;
    }

    public void setDiscovery(ConsulDiscoveryProperties discovery) {
        this.discovery = discovery;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
