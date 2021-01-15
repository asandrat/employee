package com.bamboo.employee.service.validator;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component("employee_addition")
public class AddEmployeeValidator implements Validator {
    @Override
    public boolean validate(Map<String, String> data) {
        if (data.size() != 2) {
            return false;
        }
        return data.keySet().containsAll(Arrays.asList("name", "surname"));
    }
}