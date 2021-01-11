package com.bamboo.employee.service.validationstrategy;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


@Component("employee_removal_validator")
final class RemoveEmployeeValidateStrategy implements ValidationStrategy {

    private static final Pattern idPattern = Pattern.compile("\\d+");

    @Override
    public Map<String, String> execute(Map<String, String> arguments) {

        Map<String, String> result = new HashMap<>();

        String potentialId = arguments.get("uniqueId");
        if (isValidId(potentialId)) {
            result.put("uniqueId", potentialId);
        }
        return result;
    }

    private boolean isValidId(final String potentialId) {
        return potentialId != null && idPattern.matcher(potentialId).matches();
    }
}
