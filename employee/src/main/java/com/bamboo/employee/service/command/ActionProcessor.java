package com.bamboo.employee.service.command;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ActionProcessor {

    private final Map<String, Command> commands;

    public ActionProcessor(Map<String, Command> commands) {
        this.commands = commands;
    }

    public void process(String action, Map<String, String> data) {
        commands.get(action + "_processor").execute(data);
    }
}
