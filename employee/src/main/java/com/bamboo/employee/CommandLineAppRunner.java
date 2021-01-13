package com.bamboo.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.service.CustomValidator;
import com.bamboo.employee.service.employee.EmployeeService;
import com.bamboo.employee.service.vacation.VacationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommandLineAppRunner implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationService vacationService;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Override
    public void run(final String... args) {
        LOGGER.info("Application context started with command line arguments: "
                + Arrays.toString(args));

        if (!CustomValidator.validateNumberOfArguments(args)) {
            throw new IllegalArgumentException();
        }

        if (!CustomValidator.validateArguments(args)) {
            throw new IllegalArgumentException();
        }

        Action action = Action.fromString(args[0]);
        System.out.println(action);

        List<String> arguments = Arrays.stream(args)
                .skip(1).collect(Collectors.toList());
        arguments.forEach(System.out::println);

        System.out.println("MAPA:-----------------");

        Map<String, String> data = InputParser.parseInput(arguments);
        data.entrySet().forEach(System.out::println);

        //read from file:
        Map<String, Employee> mapRead = employeeService.findAll();
        System.out.println("read from file first time:*******************");
        for (String id : mapRead.keySet()) {
            System.out.println(id + " " + mapRead.get(id).getName() + " " +
                    mapRead.get(id).getSurname());
        }

        switch (action) {
            case EMPLOYEE_ADDITION:
                employeeService.addEmployee(data.get("name"), data.get(
                        "surname"));
                break;
            case EMPLOYEE_REMOVAL:
                employeeService.removeEmployee(data.get("id"));
                break;
            case VACATION_ADDITION:
                vacationService.addVacation(
                        data.get("employeeId"), data.get("dateFrom"),
                        data.get("dateTo"), data.get("status"));
                break;
            case VACATION_REMOVAL:
                vacationService.removeVacation(data.get("id"));
                break;
            case VACATION_APPROVAL:
                vacationService.approveVacation(data.get("id"));
                break;
            case VACATION_REJECTION:
                vacationService.rejectVacation(data.get("id"));
                break;
        }


        System.out.println("Changed map---------------- ");

        Map<String, Vacation> mapVacation = vacationService.findAll();
        for (String id : mapVacation.keySet()) {
            System.out.println(mapRead
                    .get(mapVacation.get(id).getEmployeeId()).getName()
                    + "'s vacation:");
            System.out.println(id + " " + mapVacation.get(id).getStatus());
        }
    }
}
