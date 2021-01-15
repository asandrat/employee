package com.bamboo.employee;

import java.util.HashMap;
import java.util.Map;

public class InputParser {

    public static Map<String, String> parseInput(String[] arguments) {
        Map<String, String> result = new HashMap<>();
        for (String argument : arguments) {
            String[] keyValue = argument.split("=");
            result.put(keyValue[0], keyValue[1]);
        }
        return result;
    }
}
