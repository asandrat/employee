package com.bamboo.employee.validator.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class VacationRejectionValidatorTest {

    private VacationRejectionValidator vacationRejectionValidator;
    private Map<String, String> userData;

    @BeforeEach
    void setUp() {
        vacationRejectionValidator = new VacationRejectionValidator();
        userData = new HashMap<>();
    }

    @Test
    void Should_ReturnTrue_When_ParametersAreValid() {
        userData.put("uniqueId", "5678-777ABC-76AA-OO");
        userData.put("employeeUniqueId", "5678-DDD-CVB99-AA");

        boolean validationSuccess = vacationRejectionValidator.validate(userData);
        Assertions.assertTrue(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_OneParameterIsMissing() {
        userData.put("employeeUniqueId", "5678-AAA78-AVB899-AA9");

        boolean validationSuccess = vacationRejectionValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_ParametersAreInvalid() {
        userData.put("someUniqueId", "123");
        userData.put("AnotherUniqueId", "456");

        boolean validationSuccess = vacationRejectionValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_OneParameterIsInvalid() {
        userData.put("uniqueId", "5678-AAA676-CCC566-AA0");
        userData.put("employee", "ANA");

        boolean validationSuccess = vacationRejectionValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }
}
