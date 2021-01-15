package com.bamboo.employee.validator.strategy;

import com.bamboo.employee.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component("vacation_removal")
public class RemoveVacationValidator implements AbstractValidator {

    @Override
    public boolean validate(Map<String, String> userData) {

        return userData.size() == 2
                && Arrays.stream(new String[]{"uniqueId", "employeeUniqueId"})
                .allMatch(userData::containsKey);
    }
}
