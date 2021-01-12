package com.bamboo.employee.service.commandstrategy;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("employee_addition_command")
final class AddEmployeeCommand implements Command {

    private EmployeeService service;

    public AddEmployeeCommand(final EmployeeService service) {
        this.service = service;
    }

    @Override
    public void execute(final Map<String, String> params) {
        Employee employee =
                new Employee(Integer.parseInt(params.get("uniqueId")),
                        params.get("name"),
                        params.get("surname"));
        service.addEmployee(employee);
    }
}
