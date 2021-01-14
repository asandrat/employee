package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoveVacationCommand implements Command {

    private final EmployeeService employeeService;

    public RemoveVacationCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(Map<String, String> data) {
        employeeService.removeVacation(
                data.get("uniqueId"),
                data.get("employeeUniqueId")
        );
    }

    @Override
    public String getAction() {
        return "vacation_removal";
    }
}
