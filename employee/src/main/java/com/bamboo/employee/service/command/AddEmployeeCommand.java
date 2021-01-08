package com.bamboo.employee.service.command;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.EmployeeService;

import java.util.Map;

public class AddEmployeeCommand implements Command {

    private EmployeeService service;
    private Map<String, String> params;

    public AddEmployeeCommand(final EmployeeService service,
                              final Map<String, String> params) {
        this.service = service;
        this.params = params;
    }

    @Override
    public void execute() {
        Employee employee = createEmployee();
        service.addEmployee(employee);
    }

    private Employee createEmployee() {
        return new Employee(
                Integer.parseInt(params.get("uniqueId")),
                params.get("name"),
                params.get("surname"));
    }
}
