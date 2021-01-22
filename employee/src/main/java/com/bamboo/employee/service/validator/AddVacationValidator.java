package com.bamboo.employee.service.validator;

import com.bamboo.employee.model.VacationStatus;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component("vacation_addition")
public class AddVacationValidator implements Validator {
    @Override
    public boolean validate(Map<String, String> data)  {
        if (data.size() != 4) {
            return false;
        }

        if (!data.keySet().containsAll(Arrays.asList(
                "employeeId", "dateFrom", "dateTo", "status"))) {
            return false;
        }
        String dateFromString = data.get("dateFrom");
        String dateToString = data.get("dateTo");
        try {
            Date dateFrom = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(dateFromString);
            Date dateTo = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(dateToString);
            if (dateFrom.after(dateTo)) {
                return false;
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("input data not valid");
        }
        //vacation status check: status should be SUBMITTED
        Optional<VacationStatus> status = Optional.ofNullable(
                VacationStatus.fromString(data.get("status")));
        //if status is empty or != SUBMITTED: return false
        return status
                .map(vacationStatus -> vacationStatus.name()
                        .equalsIgnoreCase("SUBMITTED"))
                .orElse(false);
    }
}
