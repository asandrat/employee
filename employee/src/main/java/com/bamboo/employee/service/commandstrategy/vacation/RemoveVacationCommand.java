package com.bamboo.employee.service.commandstrategy.vacation;

import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.service.commandstrategy.Command;
import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_removal_command")
final class RemoveVacationCommand implements Command {

    private final VacationService service;

    public RemoveVacationCommand(VacationService service) {
        this.service = service;
    }

    @Override
    public void execute(Map<String, String> params) {
        VacationId id = new VacationId(
                Integer.parseInt(params.get("employeeUniqueId")),
                Integer.parseInt(params.get("uniqueId")));
        service.removeVacation(id);
    }
}
