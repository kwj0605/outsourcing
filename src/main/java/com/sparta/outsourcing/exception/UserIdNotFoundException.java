package com.sparta.outsourcing.exception;

public class UserIdNotFoundException extends NullPointerException{
    public UserIdNotFoundException() {
        super("User id를 찾을 수 없습니다.");
    }
}
