package com.bamboo.employee.service.command;


import com.bamboo.employee.service.EmployeeService;

import java.util.Map;

public interface Command {
    void execute(Map<String, String> params);
}
