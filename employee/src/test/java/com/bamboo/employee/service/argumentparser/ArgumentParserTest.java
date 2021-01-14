package com.bamboo.employee.service.argumentparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    private final ArgumentParser parser = new ArgumentParser();

    @Test
    @DisplayName("should throw when parsing illegal arguments")
    void parseData() {
        String[] badlyFormattedInput =  new String[]{"asd", "asddsa"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parseData(badlyFormattedInput));

        String[] halfBakedParams = new String[]{"name=", "surname="};
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parseData(halfBakedParams));


        String[] illegalOptions = new String[]{"asd=Petar", "xxz=Petrovski"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parseData(illegalOptions));
    }

    @Test
    void parseGoodData() {
        String[] args = new String[]{"name=asd", "surname=asds"};
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("name", "asd");
        expectedResult.put("surname", "asds");

        Assertions.assertEquals(expectedResult, parser.parseData(args));
    }
}
