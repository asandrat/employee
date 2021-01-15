package com.bamboo.employee.service.command;

import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AddVacationCommand implements Command {
    private final VacationService vacationService;

    public AddVacationCommand(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @Override
    public void execute(Map<String, String> data) {
        vacationService.addVacation(data.get("employeeId"), data.get(
                "dateFrom"), data.get("dateTo"), data.get("status"));
    }

    @Override
    public String getAction() {
        return "vacation_addition";
    }
}
