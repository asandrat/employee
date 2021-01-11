package com.bamboo.employee.service.command;

public class CommandInvoker {
    private Command command;

    public void setCommand(final Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}
