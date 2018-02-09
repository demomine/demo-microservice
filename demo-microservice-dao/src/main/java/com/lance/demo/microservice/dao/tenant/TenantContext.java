package com.lance.demo.microservice.dao.tenant;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Mybatis - 多租户拦截器
 */
public class TenantContext {

    private static ThreadLocal<String> tenant = new ThreadLocal<>();
    private static AtomicBoolean start = new AtomicBoolean(false);

    public static void set(String tenantId) {
        tenant.set(tenantId);
    }

    public static String get() {
        return tenant.get();
    }

    public static void start() {
        start.compareAndSet(false, true);
    }

    public static boolean isStarted() {
        return start.get();
    }


}
