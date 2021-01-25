package com.bamboo.employee.controller.advice;

import com.bamboo.employee.exceptions.VacationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VacationNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(VacationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vacationNotFound(VacationNotFoundException e) {
        return e.getMessage();
    }
}
