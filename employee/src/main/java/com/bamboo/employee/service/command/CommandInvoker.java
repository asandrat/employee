package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class CommandInvoker {

    @Autowired
    Map<String, Command> commands;

    public CommandInvoker(final Map<String, Command> commands) {
        this.commands = commands;
    }

    public void executeCommand(final String command,
                               final Map<String, String> validatedCommandArgs) {
        commands.get(command + "_command")
                .execute(validatedCommandArgs);
    }
}
