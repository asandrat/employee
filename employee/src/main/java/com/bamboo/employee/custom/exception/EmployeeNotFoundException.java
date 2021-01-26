package com.bamboo.employee.custom.exception;

import org.springframework.http.HttpStatus;

public class EmployeeNotFoundException extends ApplicationException {

    public EmployeeNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
