package com.bamboo.employee.service.validator;

import com.bamboo.employee.model.VacationStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component("vacation_rejection")
public class RejectVacationValidator implements Validator {
    @Override
    public boolean validate(Map<String, String> data) {
        if (data.size() != 2) {
            return false;
        }
        if (!data.keySet().containsAll(Arrays.asList(
                "id", "status"))) {
            return false;
        }
        //vacation status check: status should be REJECTED
        Optional<VacationStatus> status = Optional.ofNullable(
                VacationStatus.fromString(data.get("status")));
        //if status is empty or != REJECTED: return false
        return status
                .map(vacationStatus -> vacationStatus.name()
                        .equalsIgnoreCase("REJECTED"))
                .orElse(false);
    }
}
