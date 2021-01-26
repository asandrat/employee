package com.bamboo.employee.controller.advice;

import com.bamboo.employee.exceptions.InvalidStateTransitionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidStateTransitionAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidStateTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String exceptionHandler(InvalidStateTransitionException e) {
        return e.getMessage();
    }
}
