package com.bamboo.employee.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(final int id) {
        super("No such employee with id " + id);
    }
}
