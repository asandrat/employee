package com.bamboo.employee.service.validationstrategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_rejection_strategy")
final class VacationRejectionValidateStrategy implements ValidationStrategy {

    // same implementation as vacationApproval strategy
    // todo need to fix (DRY)
    // maybe inheritance ?

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
