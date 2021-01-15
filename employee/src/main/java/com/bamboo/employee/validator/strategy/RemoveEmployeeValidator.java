package com.bamboo.employee.validator.strategy;

import com.bamboo.employee.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("employee_removal")
public class RemoveEmployeeValidator implements AbstractValidator {

    @Override
    public boolean validate(Map<String, String> userData) {
        return userData.size() == 1
                && userData.containsKey("uniqueId");
    }
}
