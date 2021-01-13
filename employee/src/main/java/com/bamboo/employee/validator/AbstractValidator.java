package com.bamboo.employee.validator;

import java.util.Map;

public interface AbstractValidator {
    boolean validate(Map<String, String> data);
}
