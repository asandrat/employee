package com.bamboo.employee.validator.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class RemoveVacationValidatorTest {
    private RemoveVacationValidator removeVacationValidator;
    private Map<String, String> userData;

    @BeforeEach
    void setUp() {
        removeVacationValidator = new RemoveVacationValidator();
        userData = new HashMap<>();
    }

    @Test
    void Should_ReturnTrue_When_ParametersAreValid() {
        userData.put("uniqueId", "5678-AGHC76-AVBY99-OO");
        userData.put("employeeUniqueId", "5678-AGHC76-AVBY99-AA");

        boolean validationSuccess = removeVacationValidator.validate(userData);
        Assertions.assertTrue(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_OneParameterIsMissing() {
        userData.put("employeeUniqueId", "5678-AGHC76-AVBY99-AA");

        boolean validationSuccess = removeVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_ParametersAreInvalid() {
        userData.put("id", "5678-AGHC76-AVBY99-AA");
        userData.put("vacation", "Give me the vacation");

        boolean validationSuccess = removeVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_OneParameterIsInvalid() {
        userData.put("uniqueId", "5678-AAA778-BBB778-AA");
        userData.put("vacationId", "????");

        boolean validationSuccess = removeVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }
}
