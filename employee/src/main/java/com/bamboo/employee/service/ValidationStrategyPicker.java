package com.bamboo.employee.service;

import com.bamboo.employee.service.validationstrategy.AddEmployeeValidateStrategy;
import com.bamboo.employee.service.validationstrategy.RemoveEmployeeValidateStrategy;
import com.bamboo.employee.service.validationstrategy.ValidationStrategy;


public final class ValidationStrategyPicker {

    private ValidationStrategyPicker() {
    }

    public static ValidationStrategy pickStrategy(final String command) {
        switch (SupportedCommands.valueOf(command)) {
            case employee_addition:
                return new AddEmployeeValidateStrategy();
            case employee_removal:
                return new RemoveEmployeeValidateStrategy();
            case vacation_addition:
                break;
            case vacation_removal:
                break;
            default:
                return null;
        }
        return null;
    }
}
