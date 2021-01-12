package com.bamboo.employee.model;

import com.bamboo.employee.service.SupportedCommands;

public enum VacationStatus {
    SUBMITTED,
    APPROVED,
    REJECTED;

    public static boolean isSupportedStatus(final String status) {
        try {
            VacationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
