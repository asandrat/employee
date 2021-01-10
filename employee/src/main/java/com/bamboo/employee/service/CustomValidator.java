package com.bamboo.employee.service;

import com.bamboo.employee.model.VacationStatus;

import java.util.Arrays;

public class CustomValidator {

    public static boolean validateNumberOfArguments(String[] args) {
        return !(args.length < 2);
    }

    public static boolean validateAction(String arg) {
        //to do: for each action write new function to check args
        if (!arg.equalsIgnoreCase("employee_addition")
                && !arg.equalsIgnoreCase("employee_removal")
                && !arg.equalsIgnoreCase("vacation_addition")
                && !arg.equalsIgnoreCase("vacation_removal")
                && !arg.equalsIgnoreCase("vacation_approval")
                && !arg.equalsIgnoreCase("vacation_rejection")) {
            return false;
        }
        return true;
    }

    public static boolean validateArguments(String[] args) {
        //0. arg is action
        if (Arrays.stream(args).skip(1).allMatch(CustomValidator::validFormat)) {
            return true;
        }

        return false;
    }

    private static boolean validFormat(String arg) {
        String key = arg.substring(0, arg.indexOf('='));
        String value = arg.substring(arg.indexOf('=') + 1);
        //to do: value check
        if (!key.equals("uniqueId") && !key.equals("name")
                && !key.equals("surname") && !key.equals("employeeUniqueId")
                && !key.equals("dateFrom") && !key.equals("dateTo")
                && !key.equals("duration") && !key.equals("status")) {
            return false;
        }
        if (key.equals("status")) {
            VacationStatus status = VacationStatus.valueOf("status");
            //to do: status check
            //return false;
        }
        return true;
    }
}
