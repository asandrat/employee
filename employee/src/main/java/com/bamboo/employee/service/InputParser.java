package com.bamboo.employee.service;

import java.util.HashMap;
import java.util.Map;

public class InputParser {

    public static Map<String, String> parseData(String[] userInputs) {
        Map<String, String> result = new HashMap<>();
        for (String input : userInputs) {
            String[] keyValuePair= input.split("=");
            result.put(
                    keyValuePair[0],
                    keyValuePair[1]);
        }
        return result;
    }
}
