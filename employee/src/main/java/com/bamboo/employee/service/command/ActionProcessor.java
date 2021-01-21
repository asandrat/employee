package com.bamboo.employee.service.command;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ActionProcessor {

    private final Map<String, Command> commands;

    public ActionProcessor(List<Command> commands) {
        this.commands = commands.stream()
                .collect(Collectors.toMap(
                        Command::getAction,
                        command -> command));
    }

    public void process(String action, Map<String, String> data) {
        commands.get(action).execute(data);
    }
}
