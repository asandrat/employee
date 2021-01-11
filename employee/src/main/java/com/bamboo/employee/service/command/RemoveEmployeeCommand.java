package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("employee_removal_command")
final class RemoveEmployeeCommand implements Command {

    private EmployeeService service;
    private Map<String, String> params;

    public RemoveEmployeeCommand(final EmployeeService service,
                              final Map<String, String> params) {
        this.service = service;
        this.params = params;
    }

    @Override
    public void execute() {
        Integer id = Integer.parseInt(params.get("uniqueId"));
        service.removeEmployee(id);
    }

    @Override
    public Command initialize(Map<String, String> params, EmployeeService service) {
        this.params = params;
        this.service = service;
        return this;
    }
}
