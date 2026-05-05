package com.example.h20test.exception;

public class InvalidProductTypeException extends RuntimeException {

    public InvalidProductTypeException(String message) {
        super(message);
    }
}
