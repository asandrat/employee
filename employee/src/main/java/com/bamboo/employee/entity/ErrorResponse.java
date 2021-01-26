package com.bamboo.employee.entity;

import lombok.Data;

@Data
public class ErrorResponse {

    private final int status;
    private final String message;

}
