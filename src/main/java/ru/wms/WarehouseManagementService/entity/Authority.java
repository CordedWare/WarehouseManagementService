package ru.wms.WarehouseManagementService.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    USER,
    ADMIN,
    MODERATOR,

    ACCESS_TEST1,
    ACCESS_TEST2;

    @Override
    public String getAuthority() {
        return name();
    }
}
