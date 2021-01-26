package com.bamboo.employee.model;

public class ServerResponse {

    private final String message;

    public ServerResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
