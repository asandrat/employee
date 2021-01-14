package com.bamboo.employee.service.validationstrategy.vacation;

import com.bamboo.employee.service.validationstrategy.ValidationStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_removal_validator")
final class RemoveVacationValidateStrategy implements ValidationStrategy {

    @Override
    public void validate(Map<String, String> arguments) {
        String potentialId = arguments.get("uniqueId");
        String potentialEmployeeId = arguments.get("employeeUniqueId");
        if (isUniqueIdValid(potentialId)
                && isUniqueIdValid(potentialEmployeeId)) {
            return;
        }
        throw new IllegalArgumentException();
    }
}
