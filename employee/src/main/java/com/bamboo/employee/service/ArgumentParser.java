package com.bamboo.employee.service;

import java.util.HashMap;
import java.util.Map;

public final class ArgumentParser {

    private ArgumentParser() {
    }

    public static Map<String, String> parseData(final String[] args) {
        Map<String, String> result = new HashMap<>();
        for (String arg : args) {
            String[] fieldNameAndValue = arg.split("=");
            result.put(
                    fieldNameAndValue[0],
                    fieldNameAndValue[1]);
        }
        return result;
    }
}
