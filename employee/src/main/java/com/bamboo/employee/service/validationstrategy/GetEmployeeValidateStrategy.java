package com.bamboo.employee.service.validationstrategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("get_employee_validator")
final class GetEmployeeValidateStrategy implements ValidationStrategy {

    @Override
    public void validate(Map<String, String> arguments) {
        String potentialId = arguments.get("uniqueId");
        if (!isUniqueIdValid(potentialId)) {
           throw new IllegalArgumentException();
        }
    }
}
