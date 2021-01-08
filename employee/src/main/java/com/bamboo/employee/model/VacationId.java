package com.bamboo.employee.model;

import java.util.Objects;

public final class VacationId {
    private Integer employeeId;
    private Integer vacationId;

    public VacationId(Integer employeeId, Integer vacationId) {
        this.employeeId = employeeId;
        this.vacationId = vacationId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacationId that = (VacationId) o;
        return employeeId.equals(that.employeeId)
                && vacationId.equals(that.vacationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, vacationId);
    }

    @Override
    public String toString() {
        return "VacationId{"
                + "employeeId=" + employeeId
                + ", vacationId=" + vacationId
                + '}';
    }
}
