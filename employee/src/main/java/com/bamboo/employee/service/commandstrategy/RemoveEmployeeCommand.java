package com.bamboo.employee.service.commandstrategy;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("employee_removal_command")
final class RemoveEmployeeCommand implements Command {

    private EmployeeService service;

    public RemoveEmployeeCommand(final EmployeeService service) {
        this.service = service;
    }

    @Override
    public void execute(final Map<String, String> params) {
        Integer id = Integer.parseInt(params.get("uniqueId"));
        service.removeEmployee(id);
    }

}
