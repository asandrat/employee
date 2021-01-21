package com.bamboo.employee.service;

import java.util.Objects;

public enum SupportedCommands {
    EMPLOYEE_ADDITION,
    EMPLOYEE_REMOVAL,
    VACATION_ADDITION,
    VACATION_REMOVAL,
    HELP;

    public static boolean isSupportedCommand(final String command) {
        try {
            String tmp = Objects.toString(command, "");
            SupportedCommands.valueOf(tmp.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
