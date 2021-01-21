package com.bamboo.employee.service.commandstrategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class CommandInvoker {

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
