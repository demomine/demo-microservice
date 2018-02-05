package com.lance.demo.gate.ratelimit;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.zuul.filters.Route;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.StringJoiner;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.X_FORWARDED_FOR_HEADER;

@RequiredArgsConstructor
public class DefaultRateLimitKeyGenerator implements RateLimitKeyGenerator {

    private static final String ANONYMOUS_USER = "anonymous";

    private final RateLimitProperties properties;

    @Override
    public String key(final HttpServletRequest request, final Route route, final RateLimitProperties.Policy policy) {
        final List<RateLimitProperties.Policy.Type> types = policy.getType();
        final StringJoiner joiner = new StringJoiner(":");
        joiner.add(properties.getKeyPrefix());
        if (route != null) {
            joiner.add(route.getId());
        }
        if (!types.isEmpty()) {
            if (types.contains(RateLimitProperties.Policy.Type.URL) && route != null) {
                joiner.add(route.getPath());
            }
            if (types.contains(RateLimitProperties.Policy.Type.ORIGIN)) {
                joiner.add(getRemoteAddress(request));
            }
            if (types.contains(RateLimitProperties.Policy.Type.USER)) {
                joiner.add(request.getRemoteUser() != null ? request.getRemoteUser() : ANONYMOUS_USER);
            }
        }
        return joiner.toString();
    }

    private String getRemoteAddress(final HttpServletRequest request) {
        String xForwardedFor = request.getHeader(X_FORWARDED_FOR_HEADER);
        if (properties.isBehindProxy() && xForwardedFor != null) {
            return xForwardedFor;
        }
        return request.getRemoteAddr();
    }
}
