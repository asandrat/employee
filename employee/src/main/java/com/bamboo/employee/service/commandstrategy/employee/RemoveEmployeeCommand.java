package com.bamboo.employee.service.commandstrategy.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.commandstrategy.Command;
import com.bamboo.employee.service.employee.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("employee_removal_command")
final class RemoveEmployeeCommand implements Command {

    private EmployeeService service;

    public RemoveEmployeeCommand(final EmployeeService service) {
        this.service = service;
    }

    @Override
    public Employee execute(final Map<String, String> params) {
        Integer id = Integer.parseInt(params.get("uniqueId"));
        return service.removeEmployee(id);
    }

}
