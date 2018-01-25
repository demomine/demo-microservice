package com.lance.demo.gate.ratelimit;

public interface RateLimiterErrorHandler {

    void handleSaveError(String key, Exception e);
    void handleFetchError(String key, Exception e);
    void handleError(String msg, Exception e);
}
