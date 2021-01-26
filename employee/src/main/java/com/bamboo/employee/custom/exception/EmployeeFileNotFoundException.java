package com.bamboo.employee.custom.exception;

import org.springframework.http.HttpStatus;

public class EmployeeFileNotFoundException extends ApplicationException {

    public EmployeeFileNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
