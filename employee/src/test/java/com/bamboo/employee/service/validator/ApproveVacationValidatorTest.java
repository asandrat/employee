package com.bamboo.employee.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ApproveVacationValidatorTest {
    private final ApproveVacationValidator approveVacationValidator =
            new ApproveVacationValidator();
    private final Map<String, String> data = new HashMap<>();

    @Test
    void numberOfArgsShouldBeTwo(){
        data.put("id","vacationId");
        data.put("status", "approved");
        Assertions.assertTrue(approveVacationValidator.validate(data));
    }

    @Test
    void wrongNumberOfArgs(){
        Assertions.assertFalse(approveVacationValidator.validate(data));
    }

    @Test
    void wrongArgKey(){
        data.put("id","vacationId");
        data.put("name", "approved");
        Assertions.assertFalse(approveVacationValidator.validate(data));
    }

}