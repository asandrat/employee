package com.bamboo.employee.service.command;

import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApproveVacationCommand implements Command{
    private final VacationService vacationService;

    public ApproveVacationCommand(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @Override
    public void execute(Map<String, String> data) {
        vacationService.approveVacation(data.get("id"));
    }

    @Override
    public String getAction() {
        return "vacation_approval";
    }
}
