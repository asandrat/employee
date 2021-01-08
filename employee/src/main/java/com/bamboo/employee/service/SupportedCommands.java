package com.bamboo.employee.service;

public enum SupportedCommands {
    employee_addition,
    employee_removal,
    vacation_addition,
    vacation_removal,
    help;

    public static boolean isSupportedCommand(final String command) {
        try {
            SupportedCommands.valueOf(command);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
