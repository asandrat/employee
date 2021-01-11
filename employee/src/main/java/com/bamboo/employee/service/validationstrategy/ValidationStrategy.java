package com.bamboo.employee.service.validationstrategy;

import java.util.Map;

public interface ValidationStrategy {
    Map<String, String> execute(Map<String, String> arguments);
}
