package com.bamboo.employee.service.validationstrategy;

import java.util.Map;

public interface ValidationStrategy {
    Map<String, String> execute(final Map<String, String> arguments);
}
