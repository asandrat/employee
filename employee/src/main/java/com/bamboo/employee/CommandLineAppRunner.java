package com.bamboo.employee;

import com.bamboo.employee.parser.InputParser;
import com.bamboo.employee.service.EmployeeService;
import com.bamboo.employee.model.UserAction;
import com.bamboo.employee.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class CommandLineAppRunner implements CommandLineRunner {
    private final EmployeeService employeeService;
    private final Validator validator;

    public CommandLineAppRunner(
            EmployeeService employeeService,
            Validator validator) {
        this.employeeService = employeeService;
        this.validator = validator;
    }

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Override
    public void run(String... args) {
        LOGGER.info("Application context started with command line " +
                "arguments:" + Arrays.toString(args));
        System.out.println(args[0]);

        String[] userInput = Arrays.stream(args)
                .skip(1)
                .toArray(String[]::new);
        Map<String, String> data = InputParser.parseData(userInput);

        if (!UserAction.isValid(args[0])) {
            throw new IllegalArgumentException("Wrong action");
        }
        else if (validator.validate(args[0], data)) {
            UserAction action = UserAction.valueOf(args[0]);

            switch (action) {
                case employee_addition:
                    employeeService.addEmployee(
                            data.get("name"),
                            data.get("surname"));

                    break;
                case employee_removal:
                    employeeService.removeEmployee(data.get("uniqueId"));
                    break;
                case vacation_addition:
                    employeeService.addVacation(
                            data.get("employeeUniqueId"),
                            data.get("dateFrom"),
                            data.get("dateTo"),
                            data.get("status")
                    );
                    break;
                case vacation_removal:
                    employeeService.removeVacation(
                            data.get("uniqueId"),
                            data.get("employeeUniqueId")
                    );
                    break;
                case vacation_approval:
                    employeeService.approveVacation(
                            data.get("uniqueId"),
                            data.get("employeeUniqueId")
                    );
                    break;
                case vacation_rejection:
                    employeeService.rejectVacation(
                            data.get("uniqueId"),
                            data.get("employeeUniqueId")
                            );
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action);
            }
        }
    }

}
