package com.bamboo.employee.service.command;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ActionExecutor {
    private final Map<String, Command> commandLineArguments;

    public ActionExecutor(List<Command> arguments) {
        this.commandLineArguments = arguments
                .stream()
                .collect(Collectors
                        .toMap(Command::getAction, arg -> arg));
    }

    public void executeAction(String action, Map<String, String> data) {
        commandLineArguments.get(action).execute(data);
    }

}
