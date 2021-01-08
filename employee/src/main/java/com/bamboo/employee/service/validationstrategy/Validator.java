package com.bamboo.employee.service.validationstrategy;

import com.bamboo.employee.service.validationstrategy.ValidationStrategy;

import java.util.Map;

public class Validator {

    private ValidationStrategy strategy;

    public void setStrategy(ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public Map<String, String> validateAndRemoveRedundantArgs(
            final Map<String, String> arguments) {
        return strategy.execute(arguments);
    }
}
