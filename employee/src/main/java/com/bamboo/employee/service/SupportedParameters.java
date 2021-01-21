package com.bamboo.employee.service;

import java.util.Objects;

public enum SupportedParameters {
    NAME,
    SURNAME,
    UNIQUEID,
    EMPLOYEEUNIQUEID,
    FROM,
    TO,
    STATUS,
    DURATION;

    public static boolean isSupported(final String string) {
        try {
            String tmp = Objects.toString(string, "");
            SupportedParameters.valueOf(tmp.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
