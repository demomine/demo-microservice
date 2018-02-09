package com.lance.demo.microservice.dao.tenant.cache;

import com.lance.demo.microservice.dao.tenant.annotations.MultiTenantType;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class TableCache {

    private static final Map<String, TableCache> CACHE = new ConcurrentHashMap<>(100);

    private String name;
    private MultiTenantType type;
    private String column;

    private String schema;

    private TableCache(String name, MultiTenantType type, String column) {
        this(null, name, type, column);
    }

    private TableCache(String schema, String name, MultiTenantType type, String column) {
        this.name = name;
        this.type = type;
        this.column = column;
        this.schema = schema == null || schema.isEmpty() ? "" : schema + ".";
        CACHE.put(name, this);
    }

    public static TableCache get(String cacheKey) {
        return CACHE.get(cacheKey);
    }
}
