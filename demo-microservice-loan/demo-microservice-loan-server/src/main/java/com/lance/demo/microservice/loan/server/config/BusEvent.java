package com.lance.demo.microservice.loan.server.config;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import java.util.Map;

public class BusEvent extends RemoteApplicationEvent {
    private Map<String, String> values;
    public BusEvent() {
        super();
    }

    public BusEvent(Object source, String originService, String destinationService, Map<String, String> values) {
        super(source, originService, destinationService);
        this.values = values;
    }

    public Map<String, String> getValues() {
        String value = values.get("loan.status");
        if (value != null) {
            values.put("loan.status", value + value);
        }
        return values;
    }
}
