package com.lance.demo.microservice.dao.tenant;

import java.util.HashSet;
import java.util.Set;

public enum TenantOperation {
    INSERT,UPDATE,DELETE,SELECT;

    public Set<TenantOperation> defaultOperations() {
        Set<TenantOperation> tenantOperations = new HashSet<>();
        tenantOperations.add(TenantOperation.INSERT);
        tenantOperations.add(TenantOperation.UPDATE);
        tenantOperations.add(TenantOperation.SELECT);
        return tenantOperations;
    }
}
