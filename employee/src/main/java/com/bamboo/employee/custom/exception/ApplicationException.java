package com.bamboo.employee.custom.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApplicationException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

}
