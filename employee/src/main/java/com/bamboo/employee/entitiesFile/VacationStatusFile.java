package com.bamboo.employee.entitiesFile;

public enum VacationStatusFile {
    SUBMITTED,
    APPROVED,
    REJECTED;

    public static VacationStatusFile fromString(final String text) {
        for (VacationStatusFile vacationStatus : VacationStatusFile.values()) {
            if (vacationStatus.name().equalsIgnoreCase(text)) {
                return vacationStatus;
            }
        }
        return null;
    }
}
