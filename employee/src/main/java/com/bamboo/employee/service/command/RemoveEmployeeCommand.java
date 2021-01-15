package com.bamboo.employee.service.command;

import com.bamboo.employee.service.employee.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoveEmployeeCommand implements Command{
    private final EmployeeService employeeService;

    public RemoveEmployeeCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(Map<String, String> data) {
        employeeService.removeEmployee(data.get("id"));
    }

    @Override
    public String getAction() {
        return "employee_removal";
    }
}
