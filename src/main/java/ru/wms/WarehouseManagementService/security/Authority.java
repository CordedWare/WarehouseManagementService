package ru.wms.WarehouseManagementService.security;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ROLE_CUSTOMER,
    ROLE_ADMIN,
    ROLE_EMPLOYEE,

    ACCESS_TEST1,
    ACCESS_TEST2;

    @Override
    public String getAuthority() {
        return name();
    }
}
