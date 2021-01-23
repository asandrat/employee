package com.bamboo.employee.service.validator;

import com.bamboo.employee.entities.VacationStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component("vacation_approval")
public class ApproveVacationValidator implements Validator {
    @Override
    public boolean validate(Map<String, String> data) {
        if (data.size() != 2) {
            return false;
        }
        if (!data.keySet().containsAll(Arrays.asList(
                "id", "status"))) {
            return false;
        }
        //vacation status check: status should be APPROVED
        Optional<VacationStatus> status = Optional.ofNullable(
                VacationStatus.fromString(data.get("status")));
        //if status is empty or != APPROVED: return false
        return status
                .map(vacationStatus -> vacationStatus.name()
                        .equalsIgnoreCase("APPROVED"))
                .orElse(false);
    }
}
