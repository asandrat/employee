package com.bamboo.employee.service.validationstrategy.employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class GetEmployeeValidateStrategyTest {

    private final GetEmployeeValidateStrategy getStrategy = new GetEmployeeValidateStrategy();
    private final Map<String, String> args = new HashMap<>();

    @BeforeEach
    void initArgs() {
        // valid input
        args.put("uniqueId", "2");
    }

    @Test
    void badId() {
        args.put("uniqueId", "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> getStrategy.validate(args));

        args.put("uniqueId", null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> getStrategy.validate(args));

        args.put("uniqueId", " ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> getStrategy.validate(args));

        args.put("uniqueId", "123x");
        Assertions.assertThrows(IllegalArgumentException.class, () -> getStrategy.validate(args));

        args.put("uniqueId", "x");
        Assertions.assertThrows(IllegalArgumentException.class, () -> getStrategy.validate(args));
    }
}
