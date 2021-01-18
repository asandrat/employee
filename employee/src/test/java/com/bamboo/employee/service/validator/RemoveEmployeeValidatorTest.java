package com.bamboo.employee.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class RemoveEmployeeValidatorTest {
    private final RemoveEmployeeValidator removeEmployeeValidator =
            new RemoveEmployeeValidator();
    private final Map<String, String> data = new HashMap<>();

    @Test
    void numberOfArgsShouldBeOne() {
        data.put("id", "generatedId");
        Assertions.assertTrue(removeEmployeeValidator.validate(data));
    }

    @Test
    void wrongNumberOfArgs(){
        Assertions.assertFalse(removeEmployeeValidator.validate(data));
    }

    @Test
    void argKeyShouldBeId(){
        data.put("id", "generatedId");
        Assertions.assertTrue(removeEmployeeValidator.validate(data));
    }

}
