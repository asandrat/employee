package com.bamboo.employee.custom.exception;

public class VacationNotFoundException extends RuntimeException {

    public VacationNotFoundException(String message) {
        super(message);
    }
}
