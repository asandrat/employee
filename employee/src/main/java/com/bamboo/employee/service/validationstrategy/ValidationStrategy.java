package com.bamboo.employee.service.validationstrategy;

import java.util.Map;
import java.util.regex.Pattern;

public interface ValidationStrategy {
    Pattern idPattern = Pattern.compile("\\d+");

    void validate(Map<String, String> arguments);

    default boolean isUniqueIdValid(final String uniqueId) {
        return uniqueId != null && idPattern.matcher(uniqueId).matches();
    }
}
