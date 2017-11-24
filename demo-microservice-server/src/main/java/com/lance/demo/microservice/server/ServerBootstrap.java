package com.lance.demo.microservice.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
@EnableZipkinServer
public class ServerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ServerBootstrap.class, args);
    }
}
