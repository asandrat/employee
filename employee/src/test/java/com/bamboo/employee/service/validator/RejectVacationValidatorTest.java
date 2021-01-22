package com.bamboo.employee.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class RejectVacationValidatorTest {
    private final RejectVacationValidator rejectVacationValidator =
            new RejectVacationValidator();
    private final Map<String, String> data = new HashMap<>();

    @Test
    void numberOfArgsShouldBeTwo(){
        data.put("id","vacationId");
        data.put("status", "rejected");
        Assertions.assertTrue(rejectVacationValidator.validate(data));
    }

    @Test
    void wrongNumberOfArgs(){
        Assertions.assertFalse(rejectVacationValidator.validate(data));
    }

    @Test
    void wrongArgKey(){
        data.put("id","vacationId");
        data.put("name", "rejected");
        Assertions.assertFalse(rejectVacationValidator.validate(data));
    }

}