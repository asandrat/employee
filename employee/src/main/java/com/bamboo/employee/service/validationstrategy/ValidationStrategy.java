package com.bamboo.employee.service.validationstrategy;

import java.util.Map;

public interface ValidationStrategy {
    void validate(Map<String, String> arguments);

    default boolean isUniqueIdValid(final String uniqueId) {
        try {
            Integer.parseInt(uniqueId);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
