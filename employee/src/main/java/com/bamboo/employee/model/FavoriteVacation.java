package com.bamboo.employee.model;

public class FavoriteVacation {

    private int employeeId;
    private int monthValue;

    public FavoriteVacation(final int employeeId, final int monthValue) {
        this.employeeId = employeeId;
        this.monthValue = monthValue;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setEmployeeId(final int employeeId) {
        this.employeeId = employeeId;
    }

    public void setMonthValue(final int monthValue) {
        this.monthValue = monthValue;
    }
}

