package com.bamboo.employee.service.validationstrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AddEmployeeValidateStrategy implements ValidationStrategy {

    private static final Pattern NAME_PATTERN =
            Pattern.compile("[A-Z][A-Za-z]+");
    private static final Pattern SURNAME_PATTERN =
            Pattern.compile("[A-Z][a-zA-Z-]+");

    @Override
    public Map<String, String> execute(final Map<String, String> arguments) {

        Map<String, String> result = new HashMap<>();

        String potentialName = arguments.get("name");
        String potentialSurname = arguments.get("surname");
        String potentialId = arguments.get("uniqueId");
        if (isNameValid(potentialName)
                && isSurnameValid(potentialSurname)
                && isUniqueIdValid(potentialId)) {

            result.put("name", potentialName);
            result.put("surname", potentialSurname);
            result.put("uniqueId", potentialId);
            return result;
        }

        throw new IllegalArgumentException();
    }

    private boolean isUniqueIdValid(final String uniqueId) {
         try {
             Integer.parseInt(uniqueId);
         } catch (NumberFormatException e) {
             return false;
         }
         return true;
    }

    private boolean isSurnameValid(final String surname) {
        return surname != null && SURNAME_PATTERN.matcher(surname).matches();
    }

    private boolean isNameValid(final String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }


}
