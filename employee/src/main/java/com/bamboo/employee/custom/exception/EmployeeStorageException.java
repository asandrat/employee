package com.bamboo.employee.custom.exception;

import org.springframework.http.HttpStatus;

public class EmployeeStorageException extends ApplicationException {

    public EmployeeStorageException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
