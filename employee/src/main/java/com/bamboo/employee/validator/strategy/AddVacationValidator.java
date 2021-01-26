package com.bamboo.employee.validator.strategy;

import com.bamboo.employee.entities.VacationStatus;
import com.bamboo.employee.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Map;

@Component("vacation_addition")
public class AddVacationValidator implements AbstractValidator {

    @Override
    public boolean validate(Map<String, String> userData) {
        LocalDate dateFrom;
        LocalDate dateTo;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            dateFrom = LocalDate.parse(userData.get("dateFrom"), formatter);
            dateTo = LocalDate.parse(userData.get("dateTo"), formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in format dd/MM/yyyy");
            return false;
        }

        return userData.size() == 4
                && Arrays.stream(new String[]{
                "employeeUniqueId", "dateFrom", "dateTo", "status"
        })
                .allMatch(userData::containsKey)
                && dateFrom.isBefore(dateTo)
                && Arrays.stream(VacationStatus.values())
                .anyMatch(status ->
                        status.name().equalsIgnoreCase(userData.get("status"))
                );
    }
}
