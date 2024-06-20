package com.sparta.outsourcing.enums;

public enum UserStatusEnum {
    ACTIVE("Active"),
    DENIED("Denied");

    private final String userStatus;
    UserStatusEnum(String userStatus) {
        this.userStatus = userStatus;
    }
}
