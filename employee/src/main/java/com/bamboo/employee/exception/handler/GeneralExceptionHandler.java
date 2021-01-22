package com.bamboo.employee.exception.handler;

import com.bamboo.employee.custom.exception.ApplicationException;
import com.bamboo.employee.entities.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GeneralExceptionHandler {

    public ResponseEntity<ErrorResponse> handleException(
            ApplicationException exc
    ) {
        ErrorResponse error = new ErrorResponse(
                exc.getHttpStatus().value(),
                exc.getMessage()
        );

        return new ResponseEntity<>(error, exc.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RuntimeException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
