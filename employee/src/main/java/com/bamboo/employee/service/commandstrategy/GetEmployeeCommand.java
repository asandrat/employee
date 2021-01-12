package com.bamboo.employee.service.commandstrategy;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.employee.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("get_employee_command")
final class GetEmployeeCommand implements Command {

    private final EmployeeService service;

    public GetEmployeeCommand(EmployeeService service) {
        this.service = service;
    }

    @Override
    public void execute(Map<String, String> params) {
        Employee e = service.getEmployee(Integer.parseInt(params.get("uniqueId")));
        System.out.println(e);
    }
}
