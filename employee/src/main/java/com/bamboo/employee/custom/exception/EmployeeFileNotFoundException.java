package com.bamboo.employee.custom.exception;

public class EmployeeFileNotFoundException extends RuntimeException {

    public EmployeeFileNotFoundException(String message, Throwable err) {
        super(message, err);
    }
}
