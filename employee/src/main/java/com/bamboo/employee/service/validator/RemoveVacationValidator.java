package com.bamboo.employee.service.validator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("vacation_removal")
public class RemoveVacationValidator implements Validator{
    @Override
    public boolean validate(Map<String, String> data) {
        if (data.size() != 1) {
            return false;
        }
        return data.containsKey("id");
    }
}
