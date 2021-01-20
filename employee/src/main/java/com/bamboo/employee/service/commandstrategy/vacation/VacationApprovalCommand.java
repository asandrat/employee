package com.bamboo.employee.service.commandstrategy.vacation;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.service.commandstrategy.Command;
import com.bamboo.employee.service.employee.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_approval_command")
final class VacationApprovalCommand implements Command {

    private final EmployeeService service;

    public VacationApprovalCommand(EmployeeService service) {
        this.service = service;
    }

    @Override
    public Object execute(Map<String, String> params) {
        Integer empId = Integer.parseInt(params.get("employeeUniqueId"));
        Integer id = Integer.parseInt(params.get("uniqueId"));
        VacationId vacationId = new VacationId(empId, id);

        return service.approveVacation(vacationId);
    }
}
