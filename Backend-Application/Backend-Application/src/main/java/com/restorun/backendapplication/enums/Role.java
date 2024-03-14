package com.restorun.backendapplication.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    CHEF("CHEF"),
    WAITER("WAITER"),
    CUSTOMER("CUSTOMER");
    private final String role;
    Role(String role) {
        this.role = role;
    }
}
