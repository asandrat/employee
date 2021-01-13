package com.bamboo.employee.model;

import java.util.Arrays;

public enum UserAction {
    employee_addition,
    employee_removal,
    vacation_addition,
    vacation_removal,
    vacation_approval,
    vacation_rejection;

    public static boolean isValid(final String action) {
        return Arrays.stream(UserAction.values())
                .anyMatch(status ->
                        status.name().equalsIgnoreCase(action)
                );
    }
}
