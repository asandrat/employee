package com.bamboo.employee.service.validationstrategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class Validator {

    private final Map<String, ValidationStrategy> strategies;

    public Validator(Map<String, ValidationStrategy> strategies) {
        this.strategies = strategies;
    }

    public void validate(
            final String command,
            final Map<String, String> arguments) {
        strategies.get(command + "_validator").validate(arguments);
    }
}
