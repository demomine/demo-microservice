package com.lance.demo.gate.ratelimit.limitor;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lance.demo.gate.ratelimit.Rate;
import com.lance.demo.gate.ratelimit.RateLimiterErrorHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class ConsulRateLimiter extends AbstractRateLimiter {

    private final ConsulClient consulClient;
    private final ObjectMapper objectMapper;

    public ConsulRateLimiter(RateLimiterErrorHandler rateLimiterErrorHandler,
        ConsulClient consulClient, ObjectMapper objectMapper) {
        super(rateLimiterErrorHandler);
        this.consulClient = consulClient;
        this.objectMapper = objectMapper;
    }

    @Override
    protected Rate getRate(String key) {
        Rate rate = null;
        GetValue value = this.consulClient.getKVValue(key).getValue();
        if (value != null && value.getDecodedValue() != null) {
            try {
                rate = this.objectMapper.readValue(value.getDecodedValue(), Rate.class);
            } catch (IOException e) {
                log.error("Failed to deserialize Rate", e);
            }
        }
        return rate;
    }

    @Override
    protected void saveRate(Rate rate) {
        String value = "";
        try {
            value = this.objectMapper.writeValueAsString(rate);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize Rate", e);
        }

        if (hasText(value)) {
            this.consulClient.setKVValue(rate.getKey(), value);
        }
    }

}
