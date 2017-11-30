package com.lance.demo.microservice.loan.server.config;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

public class BusListener implements ApplicationListener<BusEvent> {
    @Override
    public void onApplicationEvent(BusEvent event) {
        System.out.println(event.getValues());
    }
}
