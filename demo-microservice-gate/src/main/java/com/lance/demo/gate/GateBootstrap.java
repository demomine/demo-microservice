package com.lance.demo.gate;

import com.lance.demo.framework.Bootstrap;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class GateBootstrap {
    public static void main(String[] args) {
        Bootstrap.run(GateBootstrap.class, args);
    }
}
