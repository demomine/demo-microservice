package com.lance.demo.gate.ratelimit.limitor;

import com.lance.demo.gate.ratelimit.Rate;
import com.lance.demo.gate.ratelimit.RateLimitProperties;
import com.lance.demo.gate.ratelimit.RateLimiter;
import com.lance.demo.gate.ratelimit.RateLimiterErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@RequiredArgsConstructor
public class RedisRateLimiter implements RateLimiter {

    private static final String QUOTA_SUFFIX = "-quota";

    private final RateLimiterErrorHandler rateLimiterErrorHandler;
    private final RedisTemplate redisTemplate;

    @Override
    public Rate consume(final RateLimitProperties.Policy policy, final String key, final Long requestTime) {
        final Long refreshInterval = policy.getRefreshInterval();
        final Long quota = policy.getQuota() != null ? SECONDS.toMillis(policy.getQuota()) : null;
        final Rate rate = new Rate(key, policy.getLimit(), quota, null, null);

        calcRemainingLimit(policy.getLimit(), refreshInterval, requestTime, key, rate);
        calcRemainingQuota(quota, refreshInterval, requestTime, key, rate);
        return rate;
    }

    private void calcRemainingLimit(Long limit, Long refreshInterval,
                                    Long requestTime, String key, Rate rate) {
        if (limit != null) {
            handleExpiration(key, refreshInterval, rate);
            long usage = requestTime == null ? 1L : 0L;
            Long current = 0L;
            try {
                current = this.redisTemplate.boundValueOps(key).increment(usage);
            } catch (RuntimeException e) {
                String msg = "Failed retrieving rate for " + key + ", will return limit";
                rateLimiterErrorHandler.handleError(msg, e);
            }
            rate.setRemaining(Math.max(-1, limit - current));
        }
    }

    private void calcRemainingQuota(Long quota, Long refreshInterval,
                                    Long requestTime, String key, Rate rate) {
        if (quota != null) {
            String quotaKey = key + QUOTA_SUFFIX;
            handleExpiration(quotaKey, refreshInterval, rate);
            Long usage = requestTime != null ? requestTime : 0L;
            Long current = 0L;
            try {
                current = this.redisTemplate.boundValueOps(quotaKey).increment(usage);
            } catch (RuntimeException e) {
                String msg = "Failed retrieving rate for " + quotaKey + ", will return quota limit";
                rateLimiterErrorHandler.handleError(msg, e);
            }
            rate.setRemainingQuota(Math.max(-1, quota - current));
        }
    }

    private void handleExpiration(String key, Long refreshInterval, Rate rate) {
        Long expire = null;
        try {
            expire = this.redisTemplate.getExpire(key);
            if (expire == null || expire == -1) {
                this.redisTemplate.expire(key, refreshInterval, SECONDS);
                expire = refreshInterval;
            }
        } catch (RuntimeException e) {
            String msg = "Failed retrieving expiration for " + key + ", will reset now";
            rateLimiterErrorHandler.handleError(msg, e);
        }
        rate.setReset(SECONDS.toMillis(expire == null ? 0L : expire));
    }
}
