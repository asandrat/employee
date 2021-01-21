package com.bamboo.employee.service.validationstrategy.employee;

import com.bamboo.employee.service.validationstrategy.ValidationStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


@Component("employee_removal_validator")
final class RemoveEmployeeValidateStrategy implements ValidationStrategy {


    @Override
    public void validate(Map<String, String> arguments) {
        String potentialId = arguments.get("uniqueId");
        if (!isUniqueIdValid(potentialId)) {
            throw new IllegalArgumentException();
        }
    }
}
