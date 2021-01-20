package com.bamboo.employee.service.commandstrategy.vacation;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.service.commandstrategy.Command;
import com.bamboo.employee.service.employee.EmployeeService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

@Component("vacation_addition_command")
final class AddVacationCommand implements Command {

    private final EmployeeService service;

    public AddVacationCommand(EmployeeService service) {
        this.service = service;
    }

    @Override
    public Object execute(Map<String, String> params) {
        Vacation vacation =
                new Vacation(
                        Integer.parseInt(params.get("employeeUniqueId")),
                        Integer.parseInt(params.get("uniqueId")),
                        LocalDate.parse(params.get("from")),
                        LocalDate.parse(params.get("to")),
                        params.get("duration"),
                        VacationStatus.valueOf(params.get("status")));
        service.addVacationToEmployee(vacation);
        return true;
    }
}
