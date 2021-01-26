package com.bamboo.employee.entities;

import lombok.Data;

@Data
public class ErrorResponse {

    private final int status;
    private final String message;

}
