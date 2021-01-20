package com.bamboo.employee.service.validationstrategy.vacation;

import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.service.validationstrategy.ValidationStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.regex.Pattern;

@Component("vacation_addition_validator")
final class AddVacationValidateStrategy implements ValidationStrategy {

    private static final Pattern datePattern =
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    @Override
    public void validate(Map<String, String> arguments) {
        String potentialId = arguments.get("uniqueId");
        String potentialEmployeeId = arguments.get("employeeUniqueId");
        String potentialStatus = arguments.get("status");
        String potentialFrom = arguments.get("from");
        String potentialTo = arguments.get("to");
        String potentailDuration = arguments.get("duration");

        if (!isValidStatus(potentialStatus)
                || !isUniqueIdValid(potentialEmployeeId)
                || !isUniqueIdValid(potentialId)
                || !isValidDate(potentialFrom)
                || !isValidDate(potentialTo)
                || !isDurationProper(potentailDuration, potentialFrom, potentialTo)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isDurationProper(
            final String potentailDuration,
            final String potentialFrom,
            final String potentialTo) {
        if (potentailDuration != null) {
            LocalDate start = LocalDate.parse(potentialFrom,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = LocalDate.parse(potentialTo,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return Long.valueOf(potentailDuration)
                    .equals(Math.abs(ChronoUnit.DAYS.between(end, start)));
        }
        return true;
    }

    private boolean isValidDate(final String date) {
        return date != null && datePattern.matcher(date).matches();
    }

    private boolean isValidStatus(final String potentialStatus) {
        return potentialStatus != null
                && VacationStatus.isSupportedStatus(potentialStatus);
    }

}
