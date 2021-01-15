package com.bamboo.employee.custom.exception;

public class EmployeeStorageException extends RuntimeException {

    public EmployeeStorageException(String message, Throwable err) {
        super(message, err);
    }
}
