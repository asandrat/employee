package com.bamboo.employee.service.commandstrategy;

import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_approval_command")
final class VacationApprovalCommand implements Command {

    private final VacationService service;

    public VacationApprovalCommand(VacationService service) {
        this.service = service;
    }

    @Override
    public void execute(Map<String, String> params) {
        Integer empId = Integer.parseInt(params.get("employeeUniqueId"));
        Integer id = Integer.parseInt(params.get("uniqueId"));
        VacationId vacationId = new VacationId(empId, id);

        service.approveVacation(vacationId);
    }
}
