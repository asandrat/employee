package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_addition_processor")
public class AddVacationCommand implements Command {

    private final EmployeeService employeeService;

    public AddVacationCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(Map<String, String> data) {
        employeeService.addVacation(
                data.get("employeeUniqueId"),
                data.get("dateFrom"),
                data.get("dateTo"),
                data.get("status")
        );
    }
}
