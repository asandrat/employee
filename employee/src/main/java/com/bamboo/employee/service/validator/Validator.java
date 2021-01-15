package com.bamboo.employee.service.validator;

import java.util.Map;

public interface Validator {
    boolean validate(Map<String, String> data);
}
