package com.bamboo.employee.service.command;

import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoveVacationCommand implements CommandLineArgument{
    private final VacationService vacationService;

    public RemoveVacationCommand(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @Override
    public void execute(Map<String, String> data) {
        vacationService.rejectVacation(data.get("id"));
    }

    @Override
    public String getAction() {
        return "vacation_removal";
    }
}
