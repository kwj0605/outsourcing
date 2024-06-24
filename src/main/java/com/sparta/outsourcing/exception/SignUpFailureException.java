package com.sparta.outsourcing.exception;

public class SignUpFailureException extends RuntimeException {
    public SignUpFailureException(String message) {
        super(message);
    }
}
