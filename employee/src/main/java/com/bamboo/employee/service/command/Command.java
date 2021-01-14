package com.bamboo.employee.service.command;

import java.util.Map;

public interface Command {
    void execute(Map<String, String> data);
}
