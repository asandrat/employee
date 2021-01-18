package com.bamboo.employee.validator.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class RemoveEmployeeValidatorTest {
    private RemoveEmployeeValidator removeEmployeeValidator;
    private Map<String, String> userData;

    @BeforeEach
    void setUp() {
        removeEmployeeValidator = new RemoveEmployeeValidator();
        userData = new HashMap<>();
    }

    @Test
    void Should_ReturnTrue_When_ParametersAreValid() {
        userData.put("uniqueId", "15FG7-77644AB-8788JJ");

        boolean validationSuccess = removeEmployeeValidator.validate(userData);
        Assertions.assertTrue(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_ThereIsMoreThanOneParameter() {
        userData.put("uniqueId", "15FG7-77644AB-8788JJ");
        userData.put("name", "Milena");

        boolean validationSuccess = removeEmployeeValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_KeyIsInvalid() {
        userData.put("id", "15FG7-77644AB-8788JJ");

        boolean validationSuccess = removeEmployeeValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

}
