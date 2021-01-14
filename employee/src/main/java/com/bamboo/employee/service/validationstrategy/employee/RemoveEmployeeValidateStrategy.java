package com.bamboo.employee.service.validationstrategy.employee;

import com.bamboo.employee.service.validationstrategy.ValidationStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


@Component("employee_removal_validator")
final class RemoveEmployeeValidateStrategy implements ValidationStrategy {

    private static final Pattern idPattern = Pattern.compile("\\d+");

    @Override
    public void validate(Map<String, String> arguments) {
        String potentialId = arguments.get("uniqueId");
        if (!isValidId(potentialId)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isValidId(final String potentialId) {
        return potentialId != null && idPattern.matcher(potentialId).matches();
    }
}
