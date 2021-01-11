package com.bamboo.employee.service.validationstrategy;


import java.util.Map;

public class Validator {

    private ValidationStrategy strategy;

    public void setStrategy(final ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public Map<String, String> validateAndRemoveRedundantArgs(
            final Map<String, String> arguments) {
        return strategy.execute(arguments);
    }
}
