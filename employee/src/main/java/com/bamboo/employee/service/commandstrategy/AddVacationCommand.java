package com.bamboo.employee.service.commandstrategy;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

@Component("vacation_addition_command")
public class AddVacationCommand implements Command {

    private VacationService service;

    public AddVacationCommand(VacationService service) {
        this.service = service;
    }

    @Override
    public void execute(Map<String, String> params) {
        try {
            Vacation vacation =
                    new Vacation(
                            Integer.parseInt(params.get("employeeUniqueId")),
                            Integer.parseInt(params.get("uniqueId")),
                            new SimpleDateFormat("yyyy-MM-dd").parse(params.get("from")),
                            new SimpleDateFormat("yyyy-MM-dd").parse(params.get("to")),
                            Integer.parseInt(params.get("duration")),
                            VacationStatus.valueOf(params.get("status")));
            service.addVacation(vacation);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
