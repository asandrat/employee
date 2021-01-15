package com.bamboo.employee.validator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class Validator {

    private final Map<String, AbstractValidator> validatorMap;

    public Validator(Map<String, AbstractValidator> validatorMap) {
        this.validatorMap = validatorMap;
    }

    public boolean validate(String action, Map<String, String> userData) {
        return validatorMap.get(action).validate(userData);
    }
}
