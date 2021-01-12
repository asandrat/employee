package com.bamboo.employee.service.validationstrategy;

import java.util.Map;

public interface ValidationStrategy {
    void validate(Map<String, String> arguments);
}
