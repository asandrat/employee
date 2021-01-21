package com.bamboo.employee.service.commandstrategy;


import java.util.Map;

public interface Command {
    Object execute(Map<String, String> params);
}
