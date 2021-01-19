package com.bamboo.employee.service.validationstrategy.employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RemoveEmployeeValidateStrategyTest {
    private final RemoveEmployeeValidateStrategy strategy =
            new RemoveEmployeeValidateStrategy();
    private final Map<String, String> args = new HashMap<>();

    @BeforeEach
    void initArgs() {
        // valid input
        args.put("uniqueId", "2");
    }

    @Test
    void badIdShouldThrow() {
        args.put("uniqueId", "");
        assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", null);
        assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", " ");
        assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", "123x");
        assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", "x");
        assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));
    }
}
