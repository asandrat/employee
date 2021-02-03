package com.bamboo.employee.exceptions;

import com.bamboo.employee.model.VacationId;

public class VacationNotFoundException extends RuntimeException {

    public VacationNotFoundException(final VacationId id) {
        super("Vacation with id " + id.getUniqueId() + " doesn't exists");
    }

    public VacationNotFoundException(int employeeId, int vacationId) {
        super("Vacation with id " + vacationId + " for employee " + employeeId + " doesn't exists");
    }
}