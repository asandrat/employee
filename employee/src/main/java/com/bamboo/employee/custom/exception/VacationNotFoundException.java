package com.bamboo.employee.custom.exception;

import org.springframework.http.HttpStatus;

public class VacationNotFoundException extends ApplicationException {

    public VacationNotFoundException(String message) {

        super(message, HttpStatus.NOT_FOUND);
    }
}
