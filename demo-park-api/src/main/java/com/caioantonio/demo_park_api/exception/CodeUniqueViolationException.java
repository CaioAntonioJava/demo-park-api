package com.caioantonio.demo_park_api.exception;

public class CodeUniqueViolationException extends RuntimeException {
    public CodeUniqueViolationException(String message) {
        super(message);
    }
}
