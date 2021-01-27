package com.bamboo.employee.controller;

import com.bamboo.employee.exceptions.EmployeeNotFoundException;
import com.bamboo.employee.exceptions.InvalidStateTransitionException;
import com.bamboo.employee.exceptions.VacationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String illegalArgumentHandler(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidStateTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidStateTransitionHandler(InvalidStateTransitionException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(VacationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vacationNotFoundHandler(VacationNotFoundException e) {
        return e.getMessage();
    }
}
