package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;

import java.util.Map;

public class RemoveEmployeeCommand implements Command {

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
}
