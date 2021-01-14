package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("employee_addition_processor")
public class AddEmployeeCommand implements Command {

    private final EmployeeService employeeService;

    public AddEmployeeCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(Map<String, String> data) {
        employeeService.addEmployee(
                data.get("name"),
                data.get("surname"));
    }
}
