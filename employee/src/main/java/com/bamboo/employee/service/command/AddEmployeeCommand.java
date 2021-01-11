package com.bamboo.employee.service.command;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("employee_addition_command")
final class AddEmployeeCommand implements Command {

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

    @Override
    public Command initialize(final Map<String, String> params,
                              final EmployeeService service) {
        this.params = params;
        this.service = service;
        return this;
    }

    private Employee createEmployee() {
        return new Employee(
                Integer.parseInt(params.get("uniqueId")),
                params.get("name"),
                params.get("surname"));
    }
}
