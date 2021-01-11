package com.bamboo.employee.service;

import com.bamboo.employee.service.EmployeeService;
import com.bamboo.employee.service.SupportedCommands;
import com.bamboo.employee.service.command.AddEmployeeCommand;
import com.bamboo.employee.service.command.Command;
import com.bamboo.employee.service.command.RemoveEmployeeCommand;

import java.util.Map;

public final class CommandPicker {

    private CommandPicker() {
    }

    public static Command pickCommand(final String command,
                                      final EmployeeService service,
                                      final Map<String, String> args) {
        switch (SupportedCommands.valueOf(command)) {
            case employee_addition:
                return new AddEmployeeCommand(service, args);
            case employee_removal:
                return new RemoveEmployeeCommand(service, args);
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
