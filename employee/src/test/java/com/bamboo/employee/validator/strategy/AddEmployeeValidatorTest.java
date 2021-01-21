package com.bamboo.employee.validator.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class AddEmployeeValidatorTest {

    private AddEmployeeValidator addEmployeeValidator;
    private Map<String, String> userData;

    @BeforeEach
    void setUp() {
        addEmployeeValidator = new AddEmployeeValidator();
        userData = new HashMap<>();
    }

    @Test
    void Should_ReturnTrue_When_ParametersAreValid() {
        userData.put("name", "Mila");
        userData.put("surname", "Jovic");

        boolean validationSuccess = addEmployeeValidator.validate(userData);
        Assertions.assertTrue(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_NameIsInvalid() {
        userData.put("name", "123Cao");
        userData.put("surname", "Ivanovic");

        boolean validationSuccess = addEmployeeValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_SurnameIsInvalid() {
        userData.put("name", "Ivana");
        userData.put("surname", "???");

        boolean validationSuccess = addEmployeeValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_IfSomeParameterIsMissing() {
        userData.put("name", "Rada");

        boolean validationSuccess = addEmployeeValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

    @Test
    void Should_ReturnFalse_When_KeyIsInvalid() {
        userData.put("name", "Milan");
        userData.put("age", "28");

        boolean validationSuccess = addEmployeeValidator.validate(userData);
        Assertions.assertFalse(validationSuccess);
    }

}
