package com.bamboo.employee.validator.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class VacationApprovalValidatorTest {

    private VacationApprovalValidator vacationApprovalValidator;
    private Map<String, String> userData;

    @BeforeEach
    void setUp() {
        vacationApprovalValidator = new VacationApprovalValidator();
        userData = new HashMap<>();
    }

    @Test
    void Should_ReturnTrue_When_ParametersAreValid() {
        userData.put("uniqueId", "5678-777ABC-76AA-OO");
        userData.put("employeeUniqueId", "5678-DDD-CVB99-AA");

        boolean validationSuccess = vacationApprovalValidator.validate(userData);
        Assertions.assertTrue(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_OneParameterIsMissing() {
        userData.put("employeeUniqueId", "5678-AAA78-AVB899-AA9");

        boolean validationSuccess = vacationApprovalValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_ParametersAreInvalid() {
        userData.put("someUniqueId", "AAA");
        userData.put("AnotherUniqueId", "BBB");

        boolean validationSuccess = vacationApprovalValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_OneParameterIsInvalid() {
        userData.put("uniqueId", "5678-AAA676-CCC566-AA0");
        userData.put("employee", "123");

        boolean validationSuccess = vacationApprovalValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }
}
