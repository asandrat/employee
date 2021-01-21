package com.bamboo.employee.model;

public enum VacationStatus {
    SUBMITTED,
    APPROVED,
    REJECTED;

    public static boolean isSupportedStatus(final String status) {
        try {
            VacationStatus.valueOf(status != null ? status.toUpperCase() : "");
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
