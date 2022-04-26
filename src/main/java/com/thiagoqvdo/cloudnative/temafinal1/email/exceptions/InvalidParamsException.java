package com.thiagoqvdo.cloudnative.temafinal1.email.exceptions;

public class InvalidParamsException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Request contains invalid parameters.";
    public InvalidParamsException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidParamsException(String message) {
        super(message);
    }

    public InvalidParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParamsException(Throwable cause) {
        super(cause);
    }

    public InvalidParamsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
