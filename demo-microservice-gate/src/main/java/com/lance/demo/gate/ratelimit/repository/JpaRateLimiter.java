package com.lance.demo.gate.ratelimit.repository;

import com.lance.demo.gate.ratelimit.Rate;
import com.lance.demo.gate.ratelimit.RateLimiterErrorHandler;
import com.lance.demo.gate.ratelimit.limitor.AbstractRateLimiter;

public class JpaRateLimiter extends AbstractRateLimiter {

    private final RateLimiterRepository repository;

    public JpaRateLimiter(RateLimiterErrorHandler rateLimiterErrorHandler,
        RateLimiterRepository repository) {
        super(rateLimiterErrorHandler);
        this.repository = repository;
    }

    @Override
    protected Rate getRate(String key) {
        return this.repository.findById(key).get();
    }

    @Override
    protected void saveRate(Rate rate) {
        this.repository.save(rate);
    }
}
