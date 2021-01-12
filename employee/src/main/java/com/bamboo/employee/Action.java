package com.bamboo.employee;

public enum Action {
    EMPLOYEE_ADDITION,
    EMPLOYEE_REMOVAL,
    VACATION_ADDITION,
    VACATION_REMOVAL,
    VACATION_APPROVAL,
    VACATION_REJECTION;

    public static Action fromString(final String text) {
        for (Action action : Action.values()) {
            if (action.name().equalsIgnoreCase(text)) {
                return action;
            }
        }
        return null;
    }
}
