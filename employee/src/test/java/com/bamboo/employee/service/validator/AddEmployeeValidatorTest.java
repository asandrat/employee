package com.bamboo.employee.service.validator;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class AddEmployeeValidatorTest {
    private final AddEmployeeValidator addEmployeeValidator =
            new AddEmployeeValidator();
    private final Map<String, String> data = new HashMap<>();

    @Test
    void numberOfArgsShouldBeTwo() {
        data.put("name", "Zdravko");
        data.put("surname", "Colic");
        Assertions.assertTrue(addEmployeeValidator.validate(data));
    }

    @Test
    void wrongNumberOfArgs(){
        data.put("name", "Detour");
        Assertions.assertFalse(addEmployeeValidator.validate(data));
    }

    @Test
    void argsShouldContainNameAndSurname() {
        data.put("name", "Djole");
        data.put("surname", "Balasevic");
        Assertions.assertTrue(addEmployeeValidator.validate(data));
    }
}