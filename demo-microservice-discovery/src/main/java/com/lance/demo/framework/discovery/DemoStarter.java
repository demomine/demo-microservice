package com.lance.demo.framework.discovery;

import com.lance.demo.framework.discovery.consul.ConsulListener;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DemoStarter {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoStarter.class)
                .bannerMode(Banner.Mode.OFF)
                // .listeners(new ConsulListener())
                // .initializers(new ConsulInititializer ())
                // .environment(null)
                // .properties(null)
                .registerShutdownHook(true)
                .run(args);
    }
}
