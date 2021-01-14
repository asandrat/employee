package com.bamboo.employee.service.commandstrategy;


import java.util.Map;

public interface Command {
    void execute(Map<String, String> params);
}
