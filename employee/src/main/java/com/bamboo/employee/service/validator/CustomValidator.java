package com.bamboo.employee.service.validator;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomValidator {

    private final Map<String, Validator> validators;

    public CustomValidator(Map<String, Validator> validatorMap) {
        this.validators = validatorMap;
    }

    public boolean validate(String action, Map<String, String> userData) {
        return validators.get(action).validate(userData);
    }

}
