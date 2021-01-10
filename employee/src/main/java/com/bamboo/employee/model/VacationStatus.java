package com.bamboo.employee.model;

public enum VacationStatus {
    SUBMITTED,
    APPROVED,
    REJECTED;

    public static VacationStatus fromString(final String text) {
        for (VacationStatus vacationStatus : VacationStatus.values()) {
            if (vacationStatus.name().equalsIgnoreCase(text)) {
                return vacationStatus;
            }
        }
        return null;
    }
}
