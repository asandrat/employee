package com.bamboo.employee.service.validationstrategy.vacation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AddVacationValidateStrategyTest {

    private final AddVacationValidateStrategy strategy =
            new AddVacationValidateStrategy();
    private Map<String, String> args;

    @BeforeEach
    void init() {
        args = new HashMap<>();
        args.put("uniqueId", "1");
        args.put("employeeUniqueId", "1");
        args.put("status", "SUBMITTED");
        args.put("from", "2021-01-01");
        args.put("to", "2021-02-01");
        args.put("duration", "31");
    }

    @Test
    void validateShouldNotThrowForValidInput() {
        Assertions.assertDoesNotThrow(() -> strategy.validate(args));
    }

    @Test
    void validateShouldThrowForBadUniqueId() {
        args.put("uniqueId", "32das");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("uniqueId", null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("uniqueId", "-1");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("uniqueId", "");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("uniqueId", " ");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));
    }

    @Test
    void validateShouldThrowForBadUniqueEmpId() {
        args.put("employeeUniqueId", "32das");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("employeeUniqueId", null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("employeeUniqueId", "-1");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("employeeUniqueId", "");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("employeeUniqueId", " ");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));
    }


    @Test
    void validateShouldThrowForBadStatus() {
        args.put("status", "32das");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("status", null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("status", "-1");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("status", "");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("status", " ");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));
    }

    @Test
    void validateShouldThrowForBadFrom() {
        args.put("from", "32das");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("from", null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("from", "-1");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("from", "");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("from", " ");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("from", "2020/12/12");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("from", "2020|12|12");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("from", "2020 12 12");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));
    }

    @Test
    void validateShouldThrowForBadTo() {

        args.put("to", "32das");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("to", null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("to", "-1");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("to", "");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("to", " ");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("to", "2020/12/12");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("to", "2020|12|12");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));

        args.put("to", "2020 12 12");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));
    }

    @Test
    void durationShouldEqualToDiffOfFromTo() {
        args.put("duration", "20");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.validate(args));
    }
}
