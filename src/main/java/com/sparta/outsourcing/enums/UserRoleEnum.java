package com.sparta.outsourcing.enums;

public enum UserRoleEnum {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private final String userRole;

    UserRoleEnum(String value) {
        this.userRole = value;
    }

    public String getUserRole() {
        return this.userRole;
    }

}
