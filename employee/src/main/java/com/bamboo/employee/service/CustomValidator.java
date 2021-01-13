package com.bamboo.employee.service;

import com.bamboo.employee.InputParser;
import com.bamboo.employee.model.VacationStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CustomValidator {

    public static boolean validateNumberOfArguments(String[] args) {
        return !(args.length < 2);
    }

    public static boolean validateArguments(String[] args) {
        List<String> argsWithoutFirst =
                Arrays.stream(args).skip(1).collect(Collectors.toList());
        switch (args[0]) {
            case "employee_addition":
                return validateEmployeeAdditionAction(argsWithoutFirst);
            case "employee_removal":
                return validateEmployeeRemovalAction(argsWithoutFirst);
            case "vacation_addition":
                return validateVacationAdditionAction(argsWithoutFirst);
            case "vacation_removal":
                return validateVacationRemovalAction(argsWithoutFirst);
            case "vacation_approval":
                return validateVacationApprovalAction(argsWithoutFirst);
            case "vacation_rejection":
                return validateVacationRejectionAction(argsWithoutFirst);
            default:
                return false;
        }
    }

    private static boolean validateVacationRejectionAction(List<String> args) {
        if (args.size() != 2) {
            return false;
        }
        Map<String, String> mapKeyValueArguments = InputParser.parseInput(args);
        if (!mapKeyValueArguments.keySet().containsAll(Arrays.asList(
                "id", "status"))) {
            return false;
        }
        //vacation status check: status should be REJECTED
        Optional<VacationStatus> status = Optional.ofNullable(
                VacationStatus.fromString(mapKeyValueArguments.get("status")));
        //if status is empty or != REJECTED: return false
        return status
                .map(vacationStatus -> vacationStatus.name()
                        .equalsIgnoreCase("REJECTED"))
                .orElse(false);
    }

    private static boolean validateVacationApprovalAction(List<String> args) {
        if (args.size() != 2) {
            return false;
        }
        Map<String, String> mapKeyValueArguments = InputParser.parseInput(args);
        if (!mapKeyValueArguments.keySet().containsAll(Arrays.asList(
                "id", "status"))) {
            return false;
        }
        //vacation status check: status should be APPROVED
        Optional<VacationStatus> status = Optional.ofNullable(
                VacationStatus.fromString(mapKeyValueArguments.get("status")));
        //if status is empty or != APPROVED: return false
        return status
                .map(vacationStatus -> vacationStatus.name()
                        .equalsIgnoreCase("APPROVED"))
                .orElse(false);
    }

    private static boolean validateVacationRemovalAction(List<String> args) {
        if (args.size() != 1) {
            return false;
        }
        Map<String, String> mapKeyValueArguments = InputParser.parseInput(args);
        //to do: check if vacationId is present in employee

        return mapKeyValueArguments.containsKey("id");
    }

    private static boolean validateVacationAdditionAction(List<String> args) {
        if (args.size() != 4) {
            return false;
        }
        Map<String, String> mapKeyValueArguments = InputParser.parseInput(args);
        if (!mapKeyValueArguments.keySet().containsAll(Arrays.asList(
                "employeeId", "dateFrom", "dateTo", "status"))) {
            return false;
        }

        String dateFromString = mapKeyValueArguments.get("dateFrom");
        String dateToString = mapKeyValueArguments.get("dateTo");
        try {
            Date dateFrom = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(dateFromString);
            Date dateTo = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(dateToString);
            if(dateFrom.after(dateTo)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //vacation status check: status should be SUBMITTED
        Optional<VacationStatus> status = Optional.ofNullable(
                VacationStatus.fromString(mapKeyValueArguments.get("status")));
        //if status is empty or != SUBMITTED: return false
        return status
                .map(vacationStatus -> vacationStatus.name()
                        .equalsIgnoreCase("SUBMITTED"))
                .orElse(false);
    }

    private static boolean validateEmployeeRemovalAction(List<String> args) {
        if (args.size() != 1) {
            return false;
        }
        Map<String, String> mapKeyValueArguments = InputParser.parseInput(args);

        return mapKeyValueArguments.containsKey("id");
    }

    private static boolean validateEmployeeAdditionAction(List<String> args) {
        if (args.size() != 2) {
            return false;
        }
        Map<String, String> mapKeyValueArguments = InputParser.parseInput(args);

        //if map contains all keys it is valid form
        return mapKeyValueArguments.keySet().containsAll(Arrays.asList(
                "name", "surname"));
    }

}
