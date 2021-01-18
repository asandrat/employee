package com.bamboo.employee.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

class AddVacationValidatorTest {

    private final AddVacationValidator addVacationValidator =
            new AddVacationValidator();
    private final Map<String, String> data = new HashMap<>();

    @Test
    void numberOfArgsShouldBeFour() {
        data.put("employeeId", "1234");
        data.put("dateFrom", "2021-03-01");
        data.put("dateTo", "2021-03-05");
        data.put("status", "SUBMITTED");
        Assertions.assertTrue(addVacationValidator.validate(data));
    }

    @Test
    void wrongNumberOfArgs() {
        data.put("employeeId", "1234");
        data.put("dateFrom", "2021-03-01");
        data.put("dateTo", "2021-03-05");
        Assertions.assertFalse(addVacationValidator.validate(data));
    }

    @Test
    void wrongArgumentKey() {
        data.put("employeeId", "1234");
        data.put("dateFrom", "2021-03-01");
        data.put("dateTo", "2021-03-05");
        data.put("vacationStatus", "SUBMITTED");
        Assertions.assertFalse(addVacationValidator.validate(data));
    }

    @Test
    void dateFromIsAfterDateTo() {
        data.put("employeeId", "1234");
        data.put("dateFrom", "2021-03-05");
        data.put("dateTo", "2021-03-01");
        data.put("status", "SUBMITTED");
        Assertions.assertFalse(addVacationValidator.validate(data));
    }

    @Test
    @Disabled
    void parseExceptionExpected() {
        data.put("employeeId", "1234");
        data.put("dateFrom", "'2021-03-01");
        data.put("dateTo", "2021-03-05");
        data.put("status", "SUBMITTED");
        Assertions.assertThrows(ParseException.class,
                () -> addVacationValidator.validate(data));
    }

}