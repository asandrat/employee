package com.bamboo.employee.service.validationstrategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_approval_strategy")
final class VacationApprovalValidateStrategy implements ValidationStrategy {

    @Override
    public void validate(Map<String, String> arguments) {
        String potentialEmpId = arguments.get("employeeUniqueId");
        String potentialId = arguments.get("uniqueId");
        if(!isUniqueIdValid(potentialId)
                || !isUniqueIdValid(potentialEmpId)) {
            throw new IllegalArgumentException();
        }
    }
}
