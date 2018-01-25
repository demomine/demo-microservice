package com.lance.demo.gate.ratelimit.limitor;

import com.lance.demo.gate.ratelimit.Rate;
import com.lance.demo.gate.ratelimit.RateLimiterErrorHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In memory rate limiter configuration for dev environment.
 *
 * @author Marcos Barbero
 * @author Liel Chayoun
 * @since 2017-06-23
 */
public class InMemoryRateLimiter extends AbstractRateLimiter {

    private Map<String, Rate> repository = new ConcurrentHashMap<>();

    public InMemoryRateLimiter(RateLimiterErrorHandler rateLimiterErrorHandler) {
        super(rateLimiterErrorHandler);
    }

    @Override
    protected Rate getRate(String key) {
        return this.repository.get(key);
    }

    @Override
    protected void saveRate(Rate rate) {
        this.repository.put(rate.getKey(), rate);
    }

}
