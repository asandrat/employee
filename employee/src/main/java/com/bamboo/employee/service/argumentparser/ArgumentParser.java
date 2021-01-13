package com.bamboo.employee.service.argumentparser;

import com.bamboo.employee.service.SupportedParameters;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class ArgumentParser {

    public Map<String, String> parseData(final String[] args) {
        Map<String, String> result = new HashMap<>();
        for (String arg : args) {
            String[] fieldNameAndValue = arg.split("=");

            if (fieldNameAndValue.length != 2) {
                System.err.println("-----------------------------------------");
                System.err.println("Expected format parameterName=value");
                System.err.println("-----------------------------------------");
                throw new IllegalArgumentException();
            }
            if (!SupportedParameters.isSupported(fieldNameAndValue[0])) {
                System.err.println("-----------------------------------------");
                System.err.println("Not supported parameter:" + fieldNameAndValue[0]);
                System.err.println("-----------------------------------------");
                throw new IllegalArgumentException();
            }

            result.put(fieldNameAndValue[0], fieldNameAndValue[1]);
        }
        return result;
    }
}
