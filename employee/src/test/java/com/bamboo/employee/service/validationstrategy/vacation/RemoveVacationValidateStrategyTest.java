package com.bamboo.employee.service.validationstrategy.vacation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RemoveVacationValidateStrategyTest {

    private final RemoveVacationValidateStrategy strategy = new RemoveVacationValidateStrategy();
    private Map<String, String> args;


    @BeforeEach
    void init() {
        args = new HashMap<>();
        args.put("uniqueId", "1");
        args.put("employeeUniqueId", "1");
    }

    @Test
    void badUniqueIdInput() {
        args.put("uniqueId", "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", " ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", "x");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("uniqueId", "-1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));
    }

    @Test
    void badEmployeeIdInput() {
        args.put("employeeUniqueId", "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("employeeUniqueId", "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("employeeUniqueId", " ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("employeeUniqueId", null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("employeeUniqueId", "x");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));

        args.put("employeeUniqueId", "-1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> strategy.validate(args));
    }

    @Test
    void validInput() {
        Assertions.assertDoesNotThrow(() -> strategy.validate(args));
    }
}
