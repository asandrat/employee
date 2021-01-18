package com.bamboo.employee.validator.strategy;

import com.bamboo.employee.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

@Component("employee_addition")
public class AddEmployeeValidator implements AbstractValidator {

    private final Pattern VALID_NAME =
            Pattern.compile("[A-Z][A-Za-z]+");
    private final Pattern VALID_SURNAME=
            Pattern.compile("[A-Z][a-zA-Z']+");

    @Override
    public boolean validate(Map<String, String> userData) {

        return userData.size() == 2
                && Arrays.stream(new String[]{"name", "surname"})
                .allMatch(userData::containsKey)
                && isNameValid(userData.get("name"))
                && isSurnameValid(userData.get("surname"));
    }

    private boolean isSurnameValid(final String surname) {
        return surname != null && VALID_SURNAME.matcher(surname).matches();
    }

    private boolean isNameValid(final String name) {
        return name != null && VALID_NAME.matcher(name).matches();
    }
}
