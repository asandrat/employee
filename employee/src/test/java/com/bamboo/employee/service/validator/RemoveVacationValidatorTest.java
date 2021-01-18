package com.bamboo.employee.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class RemoveVacationValidatorTest {
    private final RemoveVacationValidator removeVacationValidator =
            new RemoveVacationValidator();
    private final Map<String, String> data = new HashMap<>();

    @Test
    void numberOfArgsShouldBeOne() {
        data.put("id", "generatedId");
        Assertions.assertTrue(removeVacationValidator.validate(data));
    }

    @Test
    void wrongNumberOfArgs(){
        Assertions.assertFalse(removeVacationValidator.validate(data));
    }

    @Test
    void argKeyShouldBeId(){
        data.put("id", "generatedId");
        Assertions.assertTrue(removeVacationValidator.validate(data));
    }

}