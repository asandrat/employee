package com.bamboo.employee.service.validationstrategy.employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class AddEmployeeValidateStrategyTest {

    private final AddEmployeeValidateStrategy addStrategy = new AddEmployeeValidateStrategy();
    private final Map<String, String> args = new HashMap<>();

    @BeforeEach
    void initArgs() {
        args.put("name", "Petar");
        args.put("surname", "O'Neal");
        args.put("uniqueId", "1");
    }

    @Test
    void addValidEmployee() {
        Assertions.assertDoesNotThrow(() -> addStrategy.validate(args));

        args.put("surname", "McLovin");
        Assertions.assertDoesNotThrow(() -> addStrategy.validate(args));
    }

    @Test
    void addInvalidNameEmps() {
        args.put("name", "A");
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));

        args.put("name", "adi");
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));

        args.put("name", "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));

        args.put("name", null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));
    }

    @Test
    void addInvalidSurnameEmps() {
        args.put("surname", "A");
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));

        args.put("surname", "adsasd");
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));

        args.put("surname", "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));

        args.put("surname", null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));
    }

    @Test
    void addInvalidIdEmps() {
        args.put("uniqueId", "1A");
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));

        args.put("uniqueId", "a1dsasd");
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));

        args.put("surname", null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStrategy.validate(args));
    }
}
