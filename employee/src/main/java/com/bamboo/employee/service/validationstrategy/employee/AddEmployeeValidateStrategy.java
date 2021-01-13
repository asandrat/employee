package com.bamboo.employee.service.validationstrategy.employee;

import com.bamboo.employee.service.validationstrategy.ValidationStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component("employee_addition_validator")
final class AddEmployeeValidateStrategy implements ValidationStrategy {

    private static final Pattern NAME_PATTERN =
            Pattern.compile("[A-Z][A-Za-z]+");
    private static final Pattern SURNAME_PATTERN =
            Pattern.compile("[A-Z][a-zA-Z']+");

    @Override
    public void validate(final Map<String, String> arguments) {
        String potentialName = arguments.get("name");
        String potentialSurname = arguments.get("surname");
        String potentialId = arguments.get("uniqueId");
        if (isNameValid(potentialName)
                && isSurnameValid(potentialSurname)
                && isUniqueIdValid(potentialId)) {
            return;
        }
        throw new IllegalArgumentException();
    }


    private boolean isSurnameValid(final String surname) {
        return surname != null && SURNAME_PATTERN.matcher(surname).matches();
    }

    private boolean isNameValid(final String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }


}
