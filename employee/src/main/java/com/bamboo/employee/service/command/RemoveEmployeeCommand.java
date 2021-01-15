package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoveEmployeeCommand implements Command {

    private final EmployeeService employeeService;

    public RemoveEmployeeCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(Map<String, String> data) {
        employeeService.removeEmployee(data.get("uniqueId"));
    }

    @Override
    public String getAction() {
        return "employee_removal";
    }
}
