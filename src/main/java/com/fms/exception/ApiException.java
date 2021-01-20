package com.fms.exception;

public class ApiException extends IllegalArgumentException {

    public ApiException(String message) {
        super(message);
    }

}
