package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RejectVacationCommand implements Command {

    private final EmployeeService employeeService;

    public RejectVacationCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(Map<String, String> data) {
        employeeService.rejectVacation(
                data.get("uniqueId"),
                data.get("employeeUniqueId")
        );
    }

    @Override
    public String getAction() {
        return "vacation_rejection";
    }
}
