package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApproveVacationCommand implements Command {

    private final EmployeeService employeeService;

    public ApproveVacationCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(Map<String, String> data) {
        employeeService.approveVacation(
                data.get("uniqueId"),
                data.get("employeeUniqueId")
        );
    }

    @Override
    public String getAction() {
        return "vacation_approval";
    }
}
