package com.lance.demo.microservice.server;

import com.lance.demo.framework.Bootstrap;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication

@EnableDiscoveryClient
@EnableAdminServer
public class ServerBootstrap {
    public static void main(String[] args) {
        Bootstrap.run(ServerBootstrap.class, args);
    }
}
