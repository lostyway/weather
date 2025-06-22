package com.weather.exceptions;

public class UserRegisterException extends RuntimeException {
    public UserRegisterException(String message) {
        super(message);
    }
}
