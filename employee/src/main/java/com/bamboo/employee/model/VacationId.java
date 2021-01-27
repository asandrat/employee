package com.bamboo.employee.model;

import java.util.Objects;

public final class VacationId {
    private Integer employeeId;
    private Integer uniqueId;

    public VacationId(final int employeeId,
                      final Integer vacationId) {
        this.employeeId = employeeId;
        this.uniqueId = vacationId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacationId that = (VacationId) o;
        return employeeId.equals(that.employeeId)
                && uniqueId.equals(that.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, uniqueId);
    }

    @Override
    public String toString() {
        return "VacationId{"
                + "employeeId=" + employeeId
                + ", vacationId=" + uniqueId
                + '}';
    }
}
