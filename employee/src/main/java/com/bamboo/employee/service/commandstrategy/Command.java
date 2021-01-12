package com.bamboo.employee.service.commandexecutionstrategy;


import java.util.Map;

public interface Command {
    void execute(Map<String, String> params);
}
