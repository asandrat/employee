package com.bamboo.employee.model;

import java.util.Arrays;

public enum UserAction {
    EMPLOYEE_ADDITION,
    EMPLOYEE_REMOVAL,
    VACATION_ADDITION,
    VACATION_REMOVAL,
    VACATION_APPROVAL,
    VACATION_REJECTION;

    public static boolean isValid(final String action) {
        return Arrays.stream(UserAction.values())
                .anyMatch(status ->
                        status.name().equalsIgnoreCase(action)
                );
    }
}
