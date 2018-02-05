package com.lance.demo.gate.ratelimit;


public interface RateLimiter {

    /**
     * @param policy      Template for which rates should be created in case there's no rate limit associated with the
     *                    key
     * @param key         Unique key that identifies a request
     * @param requestTime The total time it took to handle the request
     * @return a view of a user's rate request limit
     */
    Rate consume(RateLimitProperties.Policy policy, String key, Long requestTime);
}
