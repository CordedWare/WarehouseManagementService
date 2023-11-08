package ru.wms.WarehouseManagementService.security;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    CUSTOMER,
    ADMIN,
    EMPLOYEE,

    ACCESS_TEST1,
    ACCESS_TEST2;

    @Override
    public String getAuthority() {
        return name();
    }
}
