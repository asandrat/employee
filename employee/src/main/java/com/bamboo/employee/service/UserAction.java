package com.bamboo.employee.service;

public enum UserAction {
    employee_addition,
    employee_removal,
    vacation_addition,
    vacation_removal,
    vacation_approval,
    vacation_rejection;

    public static boolean isValid(final String action) {
        try {
            UserAction.valueOf(action);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
