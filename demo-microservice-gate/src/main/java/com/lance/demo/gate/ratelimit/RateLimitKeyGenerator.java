package com.lance.demo.gate.ratelimit;

import org.springframework.cloud.netflix.zuul.filters.Route;

import javax.servlet.http.HttpServletRequest;

/**
 * Key generator for rate limit control.
 */
public interface RateLimitKeyGenerator {

    /**
     * Returns a key based on {@link HttpServletRequest}, {@link Route} and
     *
     * @param request The {@link HttpServletRequest}
     * @param route   The {@link Route}
     * @param policy  The
     * @return Generated key
     */
    String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy);
}
