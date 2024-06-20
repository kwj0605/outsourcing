package com.sparta.outsourcing.enums;

public enum UserRoleEnum {
    USER("user"),
    ADMIN("admin");

    private final String userRole;

    UserRoleEnum(String value) {
        this.userRole = value;
    }
}
