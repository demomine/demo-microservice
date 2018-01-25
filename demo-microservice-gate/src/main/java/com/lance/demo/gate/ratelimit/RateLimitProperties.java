
package com.lance.demo.gate.ratelimit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MINUTES;


@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = RateLimitProperties.RATE_LIMIT_PREFIX)
public class RateLimitProperties {
    public static final String RATE_LIMIT_PREFIX = "framework.gateway.ratelimit";
    private Policy defaultPolicy;
    @NotNull
    private List<Policy> defaultPolicyList = Lists.newArrayList(new Policy());
    @NotNull
    private Map<String, Policy> policies = Maps.newHashMap();
    @NotNull
    private Map<String, List<Policy>> policyList = Maps.newHashMap();
    private boolean behindProxy;
    private boolean enabled;
    @NotNull
    @Value("${spring.application.name:rate-limit-application}")
    private String keyPrefix;
    @NotNull
    private Repository repository = Repository.IN_MEMORY;

    public enum Repository {
        REDIS, CONSUL, JPA, IN_MEMORY
    }

    public List<Policy> getPolicies(String key) {
        return policyList.getOrDefault(key, defaultPolicyList);
    }

    @Data
    @NoArgsConstructor
    public static class Policy {

        @NotNull
        private Long refreshInterval = MINUTES.toSeconds(1L);

        private Long limit =2L;

        private Long quota;

        @NotNull
        private List<Type> type = Lists.newArrayList();

        public enum Type {
            ORIGIN, USER, URL
        }
    }
}