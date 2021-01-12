package com.bamboo.employee;

public enum Action {
    employee_addition,
    employee_removal,
    vacation_addition,
    vacation_removal,
    vacation_approval,
    vacation_rejection;

    public static Action fromString(final String text) {
        for (Action action : Action.values()) {
            if (action.name().equalsIgnoreCase(text)) {
                return action;
            }
        }
        return null;
    }
}
