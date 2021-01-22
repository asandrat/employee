package com.bamboo.employee.custom.exception;

import org.springframework.http.HttpStatus;

public class InvalidStateTransitionException extends ApplicationException {

    public InvalidStateTransitionException(String message) {

        super(message, HttpStatus.BAD_REQUEST);
    }
}
