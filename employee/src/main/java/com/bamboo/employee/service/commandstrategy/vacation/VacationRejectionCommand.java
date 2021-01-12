package com.bamboo.employee.service.commandstrategy.vacation;

import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.service.commandstrategy.Command;
import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_rejection_command")
final class VacationRejectionCommand implements Command {

    private final VacationService service;

    public VacationRejectionCommand(VacationService service) {
        this.service = service;
    }

    @Override
    public void execute(Map<String, String> params) {
        Integer empId = Integer.parseInt(params.get("employeeUniqueId"));
        Integer id = Integer.parseInt(params.get("uniqueId"));
        VacationId vacationId = new VacationId(empId, id);

        service.rejectVacation(vacationId);
    }
}
