package com.bamboo.employee.service.validationstrategy;

import com.bamboo.employee.model.VacationStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_addition_validator")
public class AddVacationValidateStrategy implements ValidationStrategy {

    @Override
    public void validate(Map<String, String> arguments) {
        String potentialId = arguments.get("uniqueId");
        String potentialEmployeeId = arguments.get("employeeUniqueId");
        String potentialStatus = arguments.get("status");
        String potentialFrom = arguments.get("from");
        String potentialTo = arguments.get("to");

        if (isValidStatus(potentialStatus)
                && isUniqueIdValid(potentialEmployeeId)
                && isUniqueIdValid(potentialId)
                && isValidDate(potentialFrom)
                && isValidDate(potentialTo)) {
            return;
        }
        throw new IllegalArgumentException();
    }

    private boolean isValidDate(final String potentialTo) {
        return true;
    }

    private boolean isValidStatus(final String potentialStatus) {
        return potentialStatus != null
                && VacationStatus.isSupportedStatus(potentialStatus);
    }

}
