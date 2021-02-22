package com.bamboo.employee.exceptions;

import com.bamboo.employee.model.VacationId;

public class VacationNotFoundException extends RuntimeException {

    public VacationNotFoundException(final VacationId id) {
        super(String.format("Vacation with id=%d doesn't exists", id.getUniqueId()));
    }

    public VacationNotFoundException(int employeeId, int vacationId) {
        super(String.format(
                "Vacation with id=%d doesn't exists "
                        + "for employee with id=%d", vacationId, employeeId));
    }
}
