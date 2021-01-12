package com.bamboo.employee.service.argumentparser;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class ArgumentParser {

    public Map<String, String> parseData(final String[] args) {
        Map<String, String> result = new HashMap<>();
        for (String arg : args) {
            String[] fieldNameAndValue = arg.split("=");
            if (fieldNameAndValue.length == 2) {
                result.put(
                        fieldNameAndValue[0],
                        fieldNameAndValue[1]);
            } else {
                System.out.println("Failed to parse argument: " + arg);
                System.out.println("Expected format parameterName=value");
                throw new IllegalArgumentException();
            }
        }
        return result;
    }
}
