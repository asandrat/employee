package com.bamboo.employee.service.command;

public class CommandInvoker {
    Command command;

    public void setCommand(final Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}
