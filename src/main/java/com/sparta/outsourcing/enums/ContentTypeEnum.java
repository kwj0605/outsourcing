package com.sparta.outsourcing.enums;

public enum ContentTypeEnum {
    RESTAURANT("restaurant"),
    REVIEW("review");

    private final String contentType;

    ContentTypeEnum(String value) {
        this.contentType = value;
    }
}
