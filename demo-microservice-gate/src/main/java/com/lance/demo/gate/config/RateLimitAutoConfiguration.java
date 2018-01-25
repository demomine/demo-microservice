package com.lance.demo.gate.config;

import com.ecwid.consul.v1.ConsulClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lance.demo.gate.ratelimit.*;
import com.lance.demo.gate.ratelimit.filter.RateLimitPostFilter;
import com.lance.demo.gate.ratelimit.filter.RateLimitPreFilter;
import com.lance.demo.gate.ratelimit.limitor.ConsulRateLimiter;
import com.lance.demo.gate.ratelimit.limitor.InMemoryRateLimiter;
import com.lance.demo.gate.ratelimit.limitor.RedisRateLimiter;
import com.lance.demo.gate.ratelimit.repository.JpaRateLimiter;
import com.lance.demo.gate.ratelimit.repository.RateLimiterRepository;
import com.netflix.zuul.ZuulFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.consul.ConditionalOnConsulEnabled;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.util.UrlPathHelper;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
@ConditionalOnProperty(prefix = RateLimitProperties.RATE_LIMIT_PREFIX, name = "enabled", havingValue = "true")
public class RateLimitAutoConfiguration implements Ordered{
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Bean
    @ConditionalOnMissingBean(RateLimiterErrorHandler.class)
    public RateLimiterErrorHandler rateLimiterErrorHandler() {
        return new DefaultRateLimiterErrorHandler();
    }

    @Bean
    public ZuulFilter rateLimiterPreFilter(final RateLimiter rateLimiter,
                                           final RateLimitProperties rateLimitProperties,
                                           final RouteLocator routeLocator,
                                           final RateLimitKeyGenerator rateLimitKeyGenerator) {
        return new RateLimitPreFilter(rateLimitProperties, routeLocator, urlPathHelper, rateLimiter,
                rateLimitKeyGenerator);
    }

    @Bean
    public ZuulFilter rateLimiterPostFilter(final RateLimiter rateLimiter,
                                            final RateLimitProperties rateLimitProperties,
                                            final RouteLocator routeLocator,
                                            final RateLimitKeyGenerator rateLimitKeyGenerator) {
        return new RateLimitPostFilter(rateLimitProperties, routeLocator, urlPathHelper, rateLimiter,
                rateLimitKeyGenerator);
    }

    @Bean
    @ConditionalOnMissingBean(RateLimitKeyGenerator.class)
    public RateLimitKeyGenerator ratelimitKeyGenerator(final RateLimitProperties properties) {
        return new DefaultRateLimitKeyGenerator(properties);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @ConditionalOnClass(RedisTemplate.class)
    @ConditionalOnMissingBean(RateLimiter.class)
    @ConditionalOnProperty(prefix = RateLimitProperties.RATE_LIMIT_PREFIX, name = "repository", havingValue = "REDIS")
    public static class RedisConfiguration {

        @Bean("rateLimiterRedisTemplate")
        public StringRedisTemplate redisTemplate(final RedisConnectionFactory connectionFactory) {
            return new StringRedisTemplate(connectionFactory);
        }

        @Bean
        public RateLimiter redisRateLimiter(RateLimiterErrorHandler rateLimiterErrorHandler,
                                            @Qualifier("rateLimiterRedisTemplate") final RedisTemplate redisTemplate) {
            return new RedisRateLimiter(rateLimiterErrorHandler, redisTemplate);
        }
    }

    @ConditionalOnConsulEnabled
    @ConditionalOnMissingBean(RateLimiter.class)
    @ConditionalOnProperty(prefix = RateLimitProperties.RATE_LIMIT_PREFIX, name = "repository", havingValue = "CONSUL")
    public static class ConsulConfiguration {

        @Bean
        public RateLimiter consultRateLimiter(final RateLimiterErrorHandler rateLimiterErrorHandler,
                                              final ConsulClient consulClient, final ObjectMapper objectMapper) {
            return new ConsulRateLimiter(rateLimiterErrorHandler, consulClient, objectMapper);
        }

    }

    @ConditionalOnMissingBean(RateLimiter.class)
    @ConditionalOnProperty(prefix = RateLimitProperties.RATE_LIMIT_PREFIX, name = "repository", havingValue = "JPA")
    public static class SpringDataConfiguration {
        @Bean
        public RateLimiter springDataRateLimiter(final RateLimiterErrorHandler rateLimiterErrorHandler,
                                                 final RateLimiterRepository rateLimiterRepository) {
            return new JpaRateLimiter(rateLimiterErrorHandler, rateLimiterRepository);
        }

    }

    @ConditionalOnMissingBean(RateLimiter.class)
    @ConditionalOnProperty(prefix = RateLimitProperties.RATE_LIMIT_PREFIX, name = "repository", havingValue = "IN_MEMORY", matchIfMissing = true)
    public static class InMemoryConfiguration {
        @Bean
        public RateLimiter inMemoryRateLimiter(final RateLimiterErrorHandler rateLimiterErrorHandler) {
            return new InMemoryRateLimiter(rateLimiterErrorHandler);
        }
    }

    @Configuration
    @RequiredArgsConstructor
    protected static class RateLimitPropertiesAdjuster {

        private final RateLimitProperties rateLimitProperties;

        @PostConstruct
        public void init() {
            RateLimitProperties.Policy defaultPolicy = rateLimitProperties.getDefaultPolicy();
            if (defaultPolicy != null) {
                ArrayList<RateLimitProperties.Policy> defaultPolicies = Lists.newArrayList(defaultPolicy);
                defaultPolicies.addAll(rateLimitProperties.getDefaultPolicyList());
                rateLimitProperties.setDefaultPolicyList(defaultPolicies);
            }
            rateLimitProperties.getPolicies().forEach((route, policy) ->
                rateLimitProperties.getPolicyList().compute(route, (key, policies) -> getPolicies(policy, policies)));
        }

        private List<RateLimitProperties.Policy> getPolicies(RateLimitProperties.Policy policy, List<RateLimitProperties.Policy> policies) {
            List<RateLimitProperties.Policy> combinedPolicies = Lists.newArrayList(policy);
            if (policies != null) {
                combinedPolicies.addAll(policies);
            }
            return combinedPolicies;
        }
    }
}
