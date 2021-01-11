package com.bamboo.employee.service.validationstrategy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class Validator {

    @Autowired
    private Map<String, ValidationStrategy> strategies;

    public void setStrategies(
            final Map<String, ValidationStrategy> strategies) {
        this.strategies = strategies;
    }

    public Map<String, String> validateAndRemoveRedundantArgs(
            final String command,
            final Map<String, String> arguments) {
        return strategies.get(command + "_validator").execute(arguments);
    }
}
