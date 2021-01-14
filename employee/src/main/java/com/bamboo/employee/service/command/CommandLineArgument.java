package com.bamboo.employee.service.command;

import java.util.Map;

public interface CommandLineArgument {
    void execute(Map<String, String> data);

    String getAction();
}
