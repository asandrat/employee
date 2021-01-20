package com.bamboo.employee.service;

public enum SupportedCommands {
    EMPLOYEE_ADDITION,
    EMPLOYEE_REMOVAL,
    VACATION_ADDITION,
    VACATION_REMOVAL,
    HELP;

    public static boolean isSupportedCommand(final String command) {
        try {
            SupportedCommands.valueOf(command != null ?
                    command.toUpperCase() : "");
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
