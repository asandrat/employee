package com.bamboo.employee.validator.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class AddVacationValidatorTest {

    private AddVacationValidator addVacationValidator;
    private Map<String, String> userData;

    @BeforeEach
    void setUp() {
        addVacationValidator = new AddVacationValidator();
        userData = new HashMap<>();
    }

    @Test
    void Should_ReturnTrue_When_ParametersAreValid() {
        userData.put("employeeUniqueId", "AAA123-BBB778-CCC");
        userData.put("dateFrom", "12/05/2021");
        userData.put("dateTo", "19/05/2021");
        userData.put("status", "submitted");

        boolean validationSuccessful = addVacationValidator.validate(userData);
        Assertions.assertTrue(validationSuccessful);
    }

    @Test
    void Should_ReturnFalse_When_SomeKeyIsInvalid() {
        userData.put("uniqueId", "AAA123-BBB778-CCC");
        userData.put("dateFrom", "12/05/2021");
        userData.put("dateTo", "19/05/2021");
        userData.put("status", "submitted");

        boolean validationSuccessful = addVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccessful);
    }

    @Test
    void Should_ReturnFalse_When_SomeParameterIsMissing() {
        userData.put("uniqueId", "AAA123-BBB778-CCC");
        userData.put("dateFrom", "12/05/2021");
        userData.put("dateTo", "19/05/2021");

        boolean validationSuccessful = addVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccessful);
    }

    @Test
    void Should_ReturnFalse_When_DateFormatIsInvalid() {
        userData.put("uniqueId", "AAA123-BBB778-CCC");
        userData.put("dateFrom", "12.05.2021");
        userData.put("dateTo", "19.05.2021");
        userData.put("status", "approved");

        boolean validationSuccessful = addVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccessful);
    }

    @Test
    void Should_ReturnFalse_When_StatusIsInvalid() {
        userData.put("uniqueId", "AAA123-BBB778-CCC");
        userData.put("dateFrom", "12/05/2021");
        userData.put("dateTo", "19/05/2021");
        userData.put("status", "waitingForApprove");

        boolean validationSuccessful = addVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccessful);
    }

    @Test
    void Should_ReturnFalse_When_DateToIsBeforeDateFrom() {
        userData.put("uniqueId", "AAA123-BBB778-CCC");
        userData.put("dateFrom", "12/05/2021");
        userData.put("dateTo", "09/05/2021");
        userData.put("status", "rejected");

        boolean validationSuccessful = addVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccessful);
    }

    @Test
    void Should_ReturnFalse_When_ThereAreMoreParametersThanExpected() {
        userData.put("uniqueId", "AAA123-BBB778-CCC");
        userData.put("dateFrom", "12/05/2021");
        userData.put("dateTo", "09/05/2021");
        userData.put("status", "approved");
        userData.put("someAnotherKey", "someRandomValue");


        boolean validationSuccessful = addVacationValidator.validate(userData);
        Assertions.assertFalse(validationSuccessful);
    }

}
