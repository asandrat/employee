package com.bamboo.employee.custom.exception;

import org.springframework.http.HttpStatus;

public class InvalidVacationStatusException extends ApplicationException {

    public InvalidVacationStatusException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
