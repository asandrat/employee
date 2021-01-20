package com.bamboo.employee.service;

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
            SupportedParameters.valueOf(string != null ?
                    string.toUpperCase() : "");

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
