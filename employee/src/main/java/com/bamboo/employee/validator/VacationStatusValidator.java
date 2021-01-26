package com.bamboo.employee.validator;

import com.bamboo.employee.custom.exception.InvalidVacationStatusException;
import com.bamboo.employee.entity.VacationStatus;

import java.util.Arrays;

public class VacationStatusValidator {

    public static VacationStatus isValid(final String vacationStatus) {
        if (Arrays.stream(VacationStatus.values())
                .noneMatch(status ->
                        status.name().equalsIgnoreCase(vacationStatus)
                )) {
            throw new InvalidVacationStatusException(vacationStatus +
                    " is invalid status for vacation. " +
                    "Possible vacation's states are: Approved, Rejected " +
                    "and Submitted.");
        }
        return VacationStatus.valueOf(vacationStatus.toUpperCase());
    }
}
